package com.example.missingpets.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentLogin2Binding

import com.example.missingpets.R
import com.example.missingpets.models.Usuario
import com.example.missingpets.network.ApiServices2
import com.example.missingpets.network.UserLogin
import com.example.missingpets.viewModels.UserProfileViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment2 : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!

    private val user: UserProfileViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener{

            val nombre = binding.username.text.toString();
            val password = binding.password.text.toString();
            //user.traerIDLogin(nombre,password)


            val apiInterface = ApiServices2.create().getUser()

            apiInterface.enqueue( object : Callback<List<UserLogin>> {
                override fun onResponse(call: Call<List<UserLogin>>?, response: Response<List<UserLogin>>?) {

                    if(response?.body() != null){
                        var list= response.body()!!.filter{it.email==nombre && it.password==password };

                        if(list.size==0){
                            user.id=-1;
                            Toast.makeText(context, "Usuario o contraseña incorrectos " , Toast.LENGTH_SHORT).show()
                        } else {
                            user.id=list.first().id
                            findNavController().navigate(R.id.action_loginFragment2_to_missingFragment2)
                            Toast.makeText(context, "Bienvenido " , Toast.LENGTH_SHORT).show()
                        }
                    }

                }

                override fun onFailure(call: Call<List<UserLogin>>, t: Throwable) {
                }
            })

        }



    }


    fun onViewCreated2(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginViewModel = LoginViewModel()
        val loginButton = binding.login

        loginViewModel.resultadoLogin.observe(viewLifecycleOwner, Observer{ resultadoLogin ->
            if(resultadoLogin.exitoso != null){
                val action = R.id.action_loginFragment2_to_missingFragment2
                findNavController().navigate(action)
            }
            else{
                loginFallido(view, resultadoLogin.error.orEmpty())
            }
        })

        loginButton.setOnClickListener {
            val usernameOrEmail = getUsernameOrEmail()
            val contrasenia = getContrasenia()
            loginViewModel.loguear(usernameOrEmail, contrasenia)
        }

        binding.tvRegistrate.setOnClickListener {
            val action = R.id.action_loginFragment2_to_registerFragment
            findNavController().navigate(action)
        }

    }

    fun getUsernameOrEmail(): String?{
        return binding.username.text.toString()
    }

    fun getContrasenia(): String?{
        return binding.password.text.toString()
    }

    fun loginFallido(view: View, mensajeError: String){
        if (mensajeError.isNotBlank()) {
            Snackbar.make(view, mensajeError, Snackbar.LENGTH_LONG).show();
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}