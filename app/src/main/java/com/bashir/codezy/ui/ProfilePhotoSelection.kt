package com.bashir.codezy.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentProfilePhotoSelectionBinding
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class ProfilePhotoSelection : Fragment() {

    private lateinit var binding: FragmentProfilePhotoSelectionBinding
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfilePhotoSelectionBinding.inflate(inflater, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        return binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.uploadPhotoButton.setOnClickListener {
            requestImageFromGallery()
        }

        binding.uploadAndSave.setOnClickListener {
            if (selectedImageUri != null) {
                uploadProfilePicture(selectedImageUri!!)
            } else {
                Snackbar.make(view, "Please select an image first", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun requestImageFromGallery() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            openGallery()
        } else {
            requestPermissions(
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            } else {
                Snackbar.make(
                    binding.root,
                    "Permission Required",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.uploadPhotoButton.setImageURI(selectedImageUri)
        }
    }

    private fun uploadProfilePicture(uri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("profilePictures/${UUID.randomUUID()}")
        val uploadTask = imageRef.putFile(uri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                updateUserProfilePicture(downloadUri.toString())
            } else {
                Snackbar.make(
                    binding.root,
                    "Failed to upload profile picture",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateUserProfilePicture(profilePictureUrl: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userRef = Firebase.firestore.collection("users").document(user.uid)
            userRef.update("profile_picture", profilePictureUrl)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Glide.with(requireContext())
                            .load(profilePictureUrl)
                            .skipMemoryCache(true)
                            .into(ImageView(context))

                        Snackbar.make(
                            binding.root,
                            "Profile picture updated successfully",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.root,
                            "Failed to update profile picture",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
        private const val PICK_IMAGE_REQUEST = 2
    }
}
