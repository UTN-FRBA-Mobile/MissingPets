package com.example.missingpets

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.missingpets.viewModels.RegisterViewModel
import com.example.missingpets.databinding.FragmentRegisterBinding
import com.example.missingpets.network.ApiServices2
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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


        viewModel.resultadoRegistro.observe(viewLifecycleOwner, Observer{ resultadoRegistro ->
            if(resultadoRegistro.exitoso != null){
                val action = R.id.action_registerFragment_to_missingFragment
                findNavController().navigate(action)
            }
            else{
                registroFallido(view, resultadoRegistro.error.orEmpty())
            }
        })

        binding.btnRegistrarse.setOnClickListener {
            val username = getUsername()
            val email = getEmail()
            val password = getContrasenia()
            val phoneNumber = getPhoneNumber()
            //viewModel.registrar(username, email, contrasenia, phoneNumber)

            if(!username.isNullOrEmpty()&&!email.isNullOrEmpty()&&!password.isNullOrEmpty()&&!phoneNumber.isNullOrEmpty()){
                try {
                    val apiInterface = ApiServices2.create()

                    val params = HashMap<String?, String?>()
                    params["username"] = username
                    params["email"] = email
                    params["password"] = password
                    params["phonenumber"] = phoneNumber

                    CoroutineScope(Dispatchers.IO).launch {


                        val response = apiInterface.insertNewUser(params)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {

                                // Convert raw JSON to pretty JSON using GSON library
                                val gson = GsonBuilder().setPrettyPrinting().create()
                                val prettyJson = gson.toJson(
                                    JsonParser.parseString(
                                        response.body()
                                            ?.string()
                                    )
                                )

                                Toast.makeText(context,"Gracias por registrarse, porfavor ingrese.",Toast.LENGTH_SHORT).show()

                                findNavController().navigate(R.id.action_registerFragment_to_loginFragment2)

                                Log.d("Pretty Printed JSON :", prettyJson)

                            } else {

                                Log.d("RETROFIT_ERROR", response.code().toString())

                            }
                        }
                    }
                } catch (e: NumberFormatException) {

                }

            }else{
                Toast.makeText(context,"Complete todos los campos.",Toast.LENGTH_SHORT).show()

            }









        }

        binding.tvIniciarSesion.setOnClickListener {
            val action = R.id.action_registerFragment_to_loginFragment2
            findNavController().navigate(action)
        }


    }

    private fun getPhoneNumber(): String?{
        return binding.etPhoneNumber.text.toString()
    }

    private fun getUsername(): String?{
        return binding.etNombreUsuarioNuevo.text.toString()
    }

    private fun getEmail(): String?{
        return binding.etEmailUsuarioNuevo.text.toString()
    }

    private fun getContrasenia(): String?{
        return binding.etContraseniaUsuarioNuevo.text.toString()
    }

    fun registroFallido(view: View, mensajeError: String){
        //val toast = Toast.makeText(context, "Complete todos los campos", Toast.LENGTH_SHORT)
        //toast.show()
        if (mensajeError.isNotBlank()) {
            Snackbar.make(view, mensajeError, Snackbar.LENGTH_LONG).show();
        }
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