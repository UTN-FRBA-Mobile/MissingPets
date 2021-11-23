package com.example.missingpets

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.example.missingpets.R.id.action_detailFragment_to_loginFragment2
import com.example.missingpets.databinding.FragmentNewPostBinding
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.Mascota
import com.google.android.material.snackbar.Snackbar
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewPostFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewPostFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentNewPostBinding? = null
    private val binding get() = _binding!!
    private val repositorioUsuario = RepositorioUsuario

    private var marcadorLatitude: Float? = null
    private var marcadorLongitude: Float? = null


    private var resultLauncherCamara = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val extras = data?.extras
            val imgBitmap = extras!!["data"] as Bitmap?
            binding.ivMascotaEncontrada.setImageBitmap(imgBitmap)

        }
    }
    private var resultLauncherGaleria = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val picture = result.data?.data
            binding.ivMascotaEncontrada.setImageURI(picture)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Use the Kotlin extension in the fragment-ktx artifact
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
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        //inflater.inflate(R.layout.fragment_post_missing, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnTomarFotoEncontrado.setOnClickListener {
            abrirCamara(view)
        }
        binding.btnSubirFotoEncontrado.setOnClickListener {
            abrirGaleria(view)
        }

        binding.dateCuando.setOnClickListener {
            showDatePickerDialog() }

        binding.btnPublicar.setOnClickListener {
            if (repositorioUsuario.noEstasLogueado()) {

                //FIXME se cierra la app cuando llega aca
                irAlLoguin()
            } else {
                Log.d("POST", "Alta de Mascota")
                var pet: Mascota = Mascota()

                //TODO asignar ID del usuario loggeado
                pet.idcreator = 0

                //TODO leer coordenadas del mapa
                pet.latitude = 40f
                pet.longitude = 30f

                pet.description = binding.etMasDetallesEncontrado.text.toString()

                //TODO hacer el post de la foto y obtener el path
                pet.photopath = "gato.jpg"

                pet.nombreMascota = binding.etNombreAnimal.text.toString()
                pet.tipoAnimal = binding.spnTipoAnimales.selectedItem.toString()
                pet.sexoAnimal = binding.spnSexoAnimales.selectedItem.toString()

                //TODO validar formato de fecha
                pet.fechaPerdido = "2021-01-10"

                if(binding.rbPerdido.isSelected()){
                    pet.estado = "perdido"
                } else {
                    pet.estado = "encontrado"
                }
                if(validarCamposVacios(pet)) {
                    publicarMascota(pet)
                }
            }
        }

        binding.imageviewMapa.setOnClickListener {
            val action = R.id.action_newPostFragment_to_mapsFragment
            val bundle = Bundle()
            //bundle.putString("latitude", binding.tvLatitude.text.toString().trim())
            //bundle.putString("longitude", binding.tvLongitude.text.toString().trim())
            findNavController().navigate(action, bundle)
        }
    }

    private fun validarCamposVacios(pet: Mascota): Boolean {

        if (pet.latitude ==0f || pet.longitude==0f) {
            Toast.makeText(requireContext(), "Es obligatiorio completar latitud y longitud",
                Toast.LENGTH_LONG).show();
            return  false
        }
        if (pet.description.isNullOrEmpty() || pet.photopath.isNullOrEmpty() ||
            pet.nombreMascota.isNullOrEmpty() || pet.tipoAnimal.isNullOrEmpty() ||
            pet.sexoAnimal.isNullOrEmpty() || pet.fechaPerdido.isNullOrEmpty() ||
            pet.estado.isNullOrEmpty()
        ) {
            Toast.makeText(requireContext(), "Hay campos obligatorios que debe completar",
                Toast.LENGTH_LONG).show();
            return false
        }
        return true
    }


    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month, year) }
        datePicker.show(childFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val realMonth = month + 1
        binding.dateCuando.setText("$day/$realMonth/$year")
    }
    private fun abrirGaleria(view: View) {
        try {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            //startActivity(intent)
            intent.type = "image/*"
            resultLauncherGaleria.launch(intent)

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir la galeria", Snackbar.LENGTH_LONG).show()
        }
    }

    // Abrir la cámara o subir desde la galeria
    private fun abrirCamara(view: View) {
        try {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (activity?.let { intent.resolveActivity(it.packageManager) } != null) {
                //startActivity(intent)
                resultLauncherCamara.launch(intent)
            } else {
                Toast.makeText(this.context, "No se encontró cámara", Toast.LENGTH_SHORT).show()
            }

        } catch (e: Exception) {
            Snackbar.make(view, "No se pudo abrir cámara", Snackbar.LENGTH_LONG).show()
        }
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
        val action = action_detailFragment_to_loginFragment2
        findNavController().navigate(action)
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
                    Log.d("SUCCESS ALTA MASCOTA", response.body()!!.toString())
                    Log.d("SUCCESS ALTA MASCOTA", response.message().toString())

                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
            }
        })
    }


}