package com.example.missingpets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.missingpets.viewModels.RegisterViewModel
import com.example.missingpets.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: RegisterViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = RegisterViewModel()


        binding.btnRegistrarse.setOnClickListener {
            val username = getUsername()
            val email = getEmail()
            val contrasenia = getContrasenia()
            if (viewModel.camposEstanVacios(username, email, contrasenia)){
                notificarCamposVacios(view)
            }else{
                viewModel.registrar(username, email, contrasenia)
            }
        }
        binding.tvIniciarSesion.setOnClickListener {
            val action = R.id.action_registerFragment_to_loginFragment2
            findNavController().navigate(action)
        }


    }

    fun getUsername(): String?{
        return binding.etNombreUsuarioNuevo.text.toString()
    }

    fun getEmail(): String?{
        return binding.etEmailUsuarioNuevo.text.toString()
    }

    fun getContrasenia(): String?{
        return binding.etContraseniaUsuarioNuevo.text.toString()
    }

    fun notificarCamposVacios(view: View){
        //val toast = Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT)
        //toast.show()
        Snackbar.make(view, "Complete todos los campos", Snackbar.LENGTH_LONG)
            .show();
    }

    fun registroExitoso(){
        //TODO
    }

    fun registroFallido(view: View){
        Snackbar.make(view, "Se perdio conexion con el servidor", Snackbar.LENGTH_LONG)
            .show();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}