package com.example.missingpets

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.os.FileUtils
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController

import com.example.missingpets.MainActivity.Companion.prefs
import com.example.missingpets.R.id.*

import com.example.missingpets.R.id.action_newPostFragment_to_loginFragment2

import com.example.missingpets.databinding.FragmentNewPostBinding
import com.example.missingpets.formats.DateFormat
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.Mascota
import com.example.missingpets.viewModels.UserProfileViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

import android.webkit.MimeTypeMap


import android.content.ContentResolver





class NewPostFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!
    private val repositorioUsuario = RepositorioUsuario
    private val user: UserProfileViewModel by activityViewModels()

    private var marcadorLatitude: Float? = null
    private var marcadorLongitude: Float? = null
    var photo: Uri? = null
    var uri: Uri? =  null
    var urlFotoEnServer: String? = null


    private var permisosCamara = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ result ->
        if (result.values.all { it }) {
            Toast.makeText(requireContext(), "Acceso a cámara concedido", Toast.LENGTH_LONG).show()
            abrirCamara()
        } else {
            Toast.makeText(requireContext(), "Acceso a cámara no concedido", Toast.LENGTH_LONG).show()
        }

    }

    private var permisosGaleria = registerForActivityResult(ActivityResultContracts.RequestPermission()){ isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(requireContext(), "Acceso a galeria concedido", Toast.LENGTH_LONG).show()
            abrirGaleria()
        } else {
            Toast.makeText(requireContext(), "Acceso a galeria no concedido", Toast.LENGTH_LONG).show()
        }

    }
    private var resultLauncherCamara = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            binding.ivMascotaEncontrada.setImageURI(photo)
            uri = photo

        }
    }
    private var resultLauncherGaleria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val picture = result.data?.data
            binding.ivMascotaEncontrada.setImageURI(picture)
            uri = picture
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // en el mapa las marca - al volver en onStart las asigna
        prefs.latitude = 0f
        prefs.longitude= 0f

     /*   // Use the Kotlin extension in the fragment-ktx artifact
        setFragmentResultListener("requestLatitude") { requestLatitude, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            marcadorLatitude = bundle.getFloat("bundleLatitude")
            // Do something with the result
            // Use the Kotlin extension in the fragment-ktx artifact
        }
        setFragmentResultListener("requestLongitude") { requestLongitude, bundle ->
            // We use a String here, but any type that can be put in a Bundle is supported
            marcadorLongitude = bundle.getFloat("bundleLongitude")
            // Do something with the result
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        if (user.id<0){
            Toast.makeText(requireContext(), "Es obligatorio estar logueado para continuar",
                Toast.LENGTH_LONG).show()
            irAlLoguin()

        }
        // Inflate the layout for this fragment
        _binding = FragmentNewPostBinding.inflate(inflater, container, false)

        //Spinner del tipo del animal
        val tipoDeAnimal = resources.getStringArray(R.array.animals)
        val spinnerAnimales = binding.spnTipoAnimales

        val adapterAnimales: ArrayAdapter<String> = initializeSpinnerAdapter(tipoDeAnimal, spinnerAnimales)
        spinnerAnimales.adapter = adapterAnimales

        // Spinner del sexo del animal
        val sexoDelAnimal = resources.getStringArray(R.array.animals_sex)
        val spinnerSexo = binding.spnSexoAnimales
        val adapter: ArrayAdapter<String> = initializeSpinnerAdapter(sexoDelAnimal, spinnerSexo)
        spinnerSexo.adapter = adapter


        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTomarFotoEncontrado.setOnClickListener {
            abrirCamara_click(view)
        }
        binding.btnSubirFotoEncontrado.setOnClickListener {
            abrirGaleria_click(view)
        }

        binding.dateCuando.setOnClickListener {
            showDatePickerDialog() }

        binding.btnPublicar.setOnClickListener {
            //stop here
            //user.id=8
            if (user.id<0){

                Toast.makeText(requireContext(), "Es obligatorio estar logueado para continuar",
                Toast.LENGTH_LONG).show()
                irAlLoguin()

            } else {
                publicarFoto(binding.ivMascotaEncontrada)
            }
        }

        binding.imageviewMapa.setOnClickListener {
            val action = R.id.action_newPostFragment_to_mapsFragment
            findNavController().navigate(action)
        }
    }

    private fun validarCamposVacios(pet: Mascota): Boolean {


        if (pet.latitude ==0f || pet.longitude==0f) {
            Toast.makeText(requireContext(), "Es obligatiorio completar ubicación",
                Toast.LENGTH_LONG).show();
            return  false
        }
        pet.photopath = urlFotoEnServer
        if (pet.description.isNullOrEmpty() || pet.photopath.isNullOrEmpty() ||
            pet.nombreMascota.isNullOrEmpty() || pet.tipoAnimal.isNullOrEmpty() ||
            pet.sexoAnimal.isNullOrEmpty() || pet.fechaPerdido.isNullOrEmpty() ||
            pet.estado.isNullOrEmpty()
        ) {
            Toast.makeText(requireContext(), "Hay campos obligatorios que debes completar",
                Toast.LENGTH_LONG).show();
            return false
        }
        return true
    }

    private fun armarMascota(): Mascota{
        //asignar ID del usuario loggeado
        var pet: Mascota = Mascota()
        pet.idcreator = user.id

        //leer coordenadas del mapa
        pet.latitude = binding.tvLatitude.text.toString().toFloat()
        pet.longitude = binding.tvLongitude.text.toString().toFloat()

        pet.description = binding.etMasDetallesEncontrado.text.toString()
        pet.photopath = urlFotoEnServer
        pet.nombreMascota = binding.etNombreAnimal.text.toString()
        pet.tipoAnimal = binding.spnTipoAnimales.selectedItem.toString()
        pet.sexoAnimal = binding.spnSexoAnimales.selectedItem.toString()

        //TODO DateFormat.ddmmyyyyToyyyymmdd(binding.dateCuando.text.toString())
        pet.fechaPerdido = DateFormat.ddmmyyyyToyyyymmdd(binding.dateCuando.text.toString())
        // get selected radio button from radioGroup
        val checkedRadioButtonId = binding.rgEstado.getCheckedRadioButtonId()
        val radioButton = view?.findViewById<RadioButton>(checkedRadioButtonId)

        if(radioButton?.text?.contains("Perd") == true){
            pet.estado = "perdido"
        } else if (radioButton?.text?.contains("Encontr") == true){
            pet.estado = "encontrado"
        }else{
            pet.estado=  null
        }
        return pet
    }


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val month = ("00"+ (month + 1).toString())
        val realMonth = month.takeLast(2)
        val day = ("00$day")
        val realDay = day.takeLast(2)
        binding.dateCuando.setText("$realDay-$realMonth-$year")
    }


    private fun abrirGaleria_click(view: View) {
        try {
            if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.READ_EXTERNAL_STORAGE) } == PackageManager.PERMISSION_DENIED) {

                permisosGaleria.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

            }else{
                abrirGaleria()
            }

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir la galeria", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun abrirGaleria(){

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncherGaleria.launch(intent)
    }

    // Abrir la cámara
    private fun abrirCamara_click(view: View) {
        try {
            if (context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.CAMERA) } == PackageManager.PERMISSION_DENIED
                || context?.let { ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) } == PackageManager.PERMISSION_DENIED) {

                    permisosCamara.launch(arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE))

            }else{
                abrirCamara()
            }

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir cámara", Snackbar.LENGTH_LONG).show()
        }
    }
    private fun abrirCamara() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "New Image")
        photo = context?.contentResolver?.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, value)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photo)
        resultLauncherCamara.launch(intent)

    }

    private fun initializeSpinnerAdapter(items: Array<String>, spinner: Spinner): ArrayAdapter<String> {

        return object : ArrayAdapter<String>( this.requireContext(),
            android.R.layout.simple_spinner_dropdown_item, items) {

            override fun getDropDownView( position: Int, convertView: View?, parent: ViewGroup): View {
                val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                // set item text bold
                view.setTypeface(view.typeface, Typeface.BOLD)

                // set selected item style
                if (position == spinner.selectedItemPosition && position != 0) {
                    view.background = ColorDrawable(Color.parseColor("#F7E7CE"))
                    view.setTextColor(Color.parseColor("#333399"))
                }

                // make hint item color gray
                if (position == 0) {
                    view.setTextColor(Color.LTGRAY)
                }

                return view
            }

            override fun isEnabled(position: Int): Boolean {
                // disable first item
                // first item is display as hint
                return position != 0
            }
        }
    }

    private fun irAlLoguin() {
        val action = action_newPostFragment_to_loginFragment2
        findNavController().navigate(action)
    }

    private fun publicarFoto(ivMascotaEncontrada: ImageView) {

        //val file = File(uri?.path)

        val realPath1 = context?.let { getRealPathFromURI(it, uri) }

        val file2 = File(realPath1)
        Log.d("UPLOAD PHOTO - uri:", file2.path) //uri.toString())

        val requestBody3 = RequestBody.create(MediaType.parse("multipart/form-data"), file2)
        val body3 = MultipartBody.Part.createFormData("fichero_usuario", file2.getName(), requestBody3)
        val descriptionString = "hello, this is description speaking"
        val description = RequestBody.create(
            MultipartBody.FORM, descriptionString
        )

        val apiInterface0 = ApiServices2.create().addPhoto(description,body3)

        apiInterface0!!.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    Log.d("UPLOAD PHOTO 1 - ", response.message().toString())
                    val responsebody =  response.body()!!
                    urlFotoEnServer = responsebody.string().substringAfterLast('/')
                    val pet = armarMascota()
                    if(validarCamposVacios(pet)) {
                        publicarMascota(pet)
                    }

                }
            }
            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
                Toast.makeText(requireContext(), "Falló publicación de foto",Toast.LENGTH_LONG).show()
            }
        })
    }

    fun publicarMascota(pet: Mascota) {
        val apiInterface0 = ApiServices2.create().addLost(
            pet.idcreator,
            pet.latitude,
            pet.longitude,
            pet.description,
            pet.photopath,
            pet.nombreMascota,
            pet.tipoAnimal,
            pet.sexoAnimal,
            pet.fechaPerdido,
            pet.estado
        )

        apiInterface0!!.enqueue(object : Callback<ResponseBody?> {

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response != null && response.isSuccessful && response.body() != null) {

                    Log.d("SUCCESS ALTA MASCOTA", response.message().toString())

                    Toast.makeText(requireContext(), "Has publicado con exito!!!",Toast.LENGTH_LONG).show()

                    if(pet.estado=="perdido"){
                        val action = R.id.action_newPostFragment_to_missingFragment
                        findNavController().navigate(action)
                    }
                    else{
                        val action = R.id.action_newPostFragment_to_adoptableFragment
                        findNavController().navigate(action)
                    }

                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
            }
        })
    }

    override fun onStart() {
        super.onStart()
        binding.tvLatitude.text = prefs.latitude.toString()
        binding.tvLongitude.text = prefs.longitude.toString()
        binding.ivMascotaEncontrada.setImageURI(uri)
    }

    fun getRealPathFromURI(context: Context, contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentUri?.let { context.contentResolver.query(it, proj, null, null, null) }
            val columnIndex: Int? = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor?.moveToFirst()
            columnIndex?.let { cursor?.getString(it) }
        } finally {
            cursor?.close()
        }

    }


}