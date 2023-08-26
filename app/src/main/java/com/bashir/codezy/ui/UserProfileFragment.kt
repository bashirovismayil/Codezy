package com.bashir.codezy.ui

import android.content.Context
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
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R
import com.bashir.codezy.adapters.PostAdapter
import com.bashir.codezy.databinding.FragmentHomeUiBinding
import com.bashir.codezy.databinding.FragmentUserProfileBinding
import com.bashir.codezy.viewmodel.HomeUIViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserProfileFragment : Fragment() {
    private lateinit var binding: FragmentUserProfileBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val user = firebaseAuth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }

        return binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val name = firebaseUser?.displayName ?: ""
        binding.userNameTextView.text = name

        binding.addProfilePictureButton.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.profilePhotoSelection)
        }
        updateUserProfilePictureInView()
    }

    private fun updateUserProfilePictureInView() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef = Firebase.firestore.collection("users").document(user.uid)
            userRef.get()
                .addOnSuccessListener { document ->
                    val profilePictureUrl = document.getString("profile_picture")
                    if (!profilePictureUrl.isNullOrEmpty()) {
                        Glide.with(requireContext())
                            .load(profilePictureUrl)
                            .transform(CircleCrop()) // Resmi yuvarlak yap
                            .into(binding.profilePicture)
                    }
                }
        }
    }
}