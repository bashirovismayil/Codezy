package com.bashir.codezy.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentRegisterMainBinding
import com.bashir.codezy.viewmodel.AuthViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterMain : Fragment() {
    private lateinit var binding: FragmentRegisterMainBinding
    private val viewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterMainBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.codezy_light_green)
        }
        return binding.root
        return view


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToLogin.setOnClickListener {
            findNavController(it).navigate(R.id.loginFragment)
        }

        binding.registerLoginButton.setOnClickListener {
            val name = binding.usernameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()


            viewModel.registerUser(email, password) {

                val user = Firebase.auth.currentUser
                if (user != null) {
                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }


                    user.updateProfile(profileUpdates).addOnCompleteListener { updateProfileTask ->
                        if (updateProfileTask.isSuccessful) {
                            val db = Firebase.firestore
                            val userDocument = db.collection("users").document(user.uid)

                            val userData = hashMapOf(
                                "name" to name,
                                "email" to email
                            )

                            userDocument.set(userData)
                                .addOnSuccessListener {
                                    val bundle = bundleOf("username" to name)
                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        requireContext(),
                                        "Firestore Error: ${exception.message}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Unknown Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }


                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Registration failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

//val postsCollection = db.collection("posts")
//postsCollection.get().addOnSuccessListener { querySnapshot ->
//    for (document in querySnapshot) {
//        val userId = document.getString("userId")
//        if (userId == user.uid) {
//            val postReference = postsCollection.document(document.id)
//            postReference.update("username", name)
//                .addOnFailureListener { exception ->
//                    // Hata durumunda işlemler
//                }
//        }
//    }
//}