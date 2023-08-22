package com.bashir.codezy.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentRegisterMainBinding
import com.bashir.codezy.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterMain : Fragment() {
    private lateinit var binding: FragmentRegisterMainBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterMainBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.codezy_light_green)
        }
        return binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerLoginButton.setOnClickListener {
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()


            viewModel.registerUser( email, password) {

                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            }

        }

        binding.goToLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginFragment)
        }


    }

}