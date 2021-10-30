package com.example.missingpets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentDetailBinding
import com.example.missingpets.databinding.FragmentRegisterBinding
import com.example.missingpets.viewModels.DetailViewModel
import com.example.missingpets.viewModels.RegisterViewModel
import java.util.*

class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!


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


    fun getArgumentoFechaPerdido(): Date{
        return Date()
    }

}