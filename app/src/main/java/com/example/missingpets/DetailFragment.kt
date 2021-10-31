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
import com.google.android.material.snackbar.Snackbar


class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    val repositorioUsuario = RepositorioUsuario


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        val nombreAnimal = getArgumentoNombreAnimal()
        val tipoAnimal = getArgumentoTipoAnimal()
        val sexoAnimal = getArgumentoSexoAnimal()
        val fechaPerdido = getArgumentoFechaPerdido()

        detailViewModel = DetailViewModel(nombreAnimal, tipoAnimal, sexoAnimal, fechaPerdido)

        completarLabels(nombreAnimal, tipoAnimal, sexoAnimal, fechaPerdido)

        binding.btnContactar.setOnClickListener {
            if (repositorioUsuario.noEstasLogueado()){
                this.irAlLoguin()
            }
            else{
                val numeroTelefono = detailViewModel.getNumeroTelefono()
                abrirWhatsapp(view, numeroTelefono)
            }
        }
    }

    fun irAlLoguin(){
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

    fun completarLabels(nombreMascota: String, tipoAnimal: String, sexoAnimal: String, fechaPerdido: String){
        completarLabelNombreAnimal(nombreMascota)
        completarLabelTipoAnimal(tipoAnimal)
        completarLabelSexoAnimal(sexoAnimal)
        completarLabelFechaPerdidoAnimal(fechaPerdido)
    }

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

}