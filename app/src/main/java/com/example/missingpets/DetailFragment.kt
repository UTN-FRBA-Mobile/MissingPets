package com.example.missingpets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentDetailBinding
import com.example.missingpets.databinding.FragmentRegisterBinding
import com.example.missingpets.models.RepositorioUsuario
import com.example.missingpets.viewModels.DetailViewModel
import com.example.missingpets.viewModels.RegisterViewModel
import java.util.*
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.missingpets.dataRV.MissingDatasource
import com.example.missingpets.dataRV.UserDatasource
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.ImageView
import com.example.missingpets.formats.DateFormat
import com.example.missingpets.network.*


class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val repositorioUsuario = RepositorioUsuario
    private var idmascota: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idmascota = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apiInterface0 = ApiServices2.create().getLostById(idmascota)

        apiInterface0.enqueue(object : Callback<List<Mascota>> {
            override fun onResponse(call: Call<List<Mascota>>?, response: Response<List<Mascota>>?) {
                if (response != null && response.isSuccessful && response.body() != null) {
                    Log.d("SUCCESS MASCOTA API", response.body()!!.toString())
                    Log.d("NOMBRE MASCOTA", response.body()?.elementAt(0)?.nombreMascota ?: "")

                    val nombreMascota = response.body()?.elementAt(0)?.nombreMascota ?: ""
                    val tipoAnimal = response.body()?.elementAt(0)?.tipoAnimal ?: ""
                    val sexoAnimal = response.body()?.elementAt(0)?.sexoAnimal ?: ""
                    var fechaPerdido = response.body()?.elementAt(0)?.fechaPerdido ?: ""
                    fechaPerdido = DateFormat.yyyymmddToddmmyyyy(fechaPerdido)
                    val longitude = response.body()!!.elementAt(0)?.longitude.toString() ?: "0.0"
                    val latitude = response.body()!!.elementAt(0)?.latitude.toString() ?: "0.0"
                    val photopath = (getActivity()?.getString(com.example.missingpets.R.string.images_root_path)
                        ?:"" ) + response.body()!!.elementAt(0)?.photopath.toString() ?: "0.0"
                    var idUsuario = response.body()?.elementAt(0)?.idcreator ?: 0

                    detailViewModel =
                        DetailViewModel(nombreMascota, tipoAnimal, sexoAnimal, fechaPerdido)

                      completarLabels(nombreMascota, tipoAnimal, sexoAnimal, fechaPerdido, latitude, longitude)

                    loadImage(photopath)

                    val apiInterface1 = ApiServices2.create().getUserById(idUsuario)

                    apiInterface1.enqueue(object : Callback<List<User>> {
                        override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                            if (response != null && response.isSuccessful && response.body() != null) {
                                idUsuario = response.body()?.elementAt(0)?.id ?: 0
                                val username = response.body()?.elementAt(0)?.username ?: ""
                                val numeroTelefono = response.body()?.elementAt(0)?.phonenumber ?: ""
                                Log.d("ERROR", "Datos uusario: " + idUsuario + " " + username + " " + numeroTelefono)
                                detailViewModel.setNumeroTelefono(numeroTelefono)
                                detailViewModel.setUsernameCreadorPost(username)
                            }
                        }
                        override fun onFailure(call: Call<List<User>>, t: Throwable) {
                            Log.e("Error:::", "Error " + t!!.message)
                        }
                    })


                    binding.btnContactar.setOnClickListener {

                        val numeroTelefono = detailViewModel.getNumeroTelefono()
                        abrirWhatsapp(view, numeroTelefono)

                    }
                    binding.imageviewMapa.setOnClickListener {
                        val action = R.id.action_detailFragment_to_mapsFragment
                        val bundle = Bundle()
                        bundle.putString("latitude", binding.tvLatitude.text.toString().trim())
                        bundle.putString("longitude", binding.tvLongitude.text.toString().trim())
                        findNavController().navigate(action, bundle)
                    }
                }
            }

            override fun onFailure(call: Call<List<Mascota>>, t: Throwable) {
                Log.e("Error:::", "Error " + t!!.message)
            }
        })
    }

        fun irAlLoguin() {
            val action = R.id.action_detailFragment_to_loginFragment2
            findNavController().navigate(action)
        }

    //El numero de telefono debe ser completo en formato internacional sin parentesis, ni guiones
    //ni nada, solo los numeros. Ejemplo: 5491169013434
    fun abrirWhatsapp(view: View, numeroTelefono: String){
        try{
            val url = "https://wa.me/$numeroTelefono"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
        catch(e: Exception){
            Snackbar.make(view, "No tienes instalado Whatsapp", Snackbar.LENGTH_LONG).show();
        }
    }

    fun completarLabelNombreAnimal(nombreAnimal: String){
        binding.tvNombre.text = nombreAnimal
    }

    fun completarLabelTipoAnimal(tipoAnimal: String){
        binding.tvTipoAnimal.text = tipoAnimal
    }

    fun completarLabelSexoAnimal(sexoAnimal: String){
        binding.tvSexoAnimal.text = sexoAnimal
    }

    fun completarLabelFechaPerdidoAnimal(fechaPerdido: String){
        binding.tvFechaPerdido.text = fechaPerdido
    }

    fun completarLabelLatitude(latitude: String){
        binding.tvLatitude.text = latitude
    }

    fun completarLabelLongitude(longitude: String){
        binding.tvLongitude.text = longitude
    }

    fun completarLabels(nombreMascota: String, tipoAnimal: String, sexoAnimal: String, fechaPerdido: String,
                latitude: String, longitude: String){
        completarLabelNombreAnimal(nombreMascota)
        completarLabelTipoAnimal(tipoAnimal)
        completarLabelSexoAnimal(sexoAnimal)
        completarLabelFechaPerdidoAnimal(fechaPerdido)
        completarLabelLatitude(latitude)
        completarLabelLongitude(longitude)
    }

    fun loadImage(photopath: String) {
        var photoview: ImageView = requireView()!!.findViewById(R.id.iv_mascota)
        Picasso.get().load(photopath).into(photoview)
    }

/*
    fun getArgumentoNombreAnimal(): String{
        return "pelusa"
    }

    fun getArgumentoTipoAnimal(): String{
        return "gato"
    }

    fun getArgumentoSexoAnimal(): String{
        return "masculino"
    }

    fun getArgumentoFechaPerdido(): String{
        return "30/10/2021"
    }

    fun getArgumentoLatitude(): Number{
        return -16
    }

    fun getArgumentoLongitude(): Number{
        return -51
    }*/

}