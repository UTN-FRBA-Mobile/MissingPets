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
import androidx.navigation.fragment.findNavController
import com.example.missingpets.databinding.FragmentLogin2Binding

import com.example.missingpets.R
import com.google.android.material.snackbar.Snackbar

class LoginFragment2 : Fragment() {

    private lateinit var loginViewModel: LoginViewModel
    private var _binding: FragmentLogin2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentLogin2Binding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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