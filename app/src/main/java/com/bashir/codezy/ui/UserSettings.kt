package com.bashir.codezy.ui

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R
import com.bashir.codezy.adapters.ProfessionAdapter
import com.bashir.codezy.databinding.FragmentUserSettingsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

class UserSettings : Fragment() {
    private lateinit var binding: FragmentUserSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeName()
        changeProfession()
        changeEmail()
        changeYourPassword()
        logOutButtonClick()


    }

    private fun changeName() {
        binding.changeName.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Change Name")

            val inputEditText = EditText(requireContext())
            inputEditText.hint = "Enter your name"
            val maxLength = 30
            val inputFilters = arrayOf<InputFilter>(InputFilter.LengthFilter(maxLength))
            inputEditText.filters = inputFilters
            builder.setView(inputEditText)

            val messageText = "Attention! This will be visible to everyone on your profile."
            val spannableMessage = SpannableString(messageText)
            spannableMessage.setSpan(RelativeSizeSpan(0.8f), 0, messageText.length, 0)
            spannableMessage.setSpan(ForegroundColorSpan(Color.RED), 0, messageText.length, 0)
            builder.setMessage(spannableMessage)

            builder.setPositiveButton("Save") { dialog, _ ->
                val newName = inputEditText.text.toString()

                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val userReference =
                        FirebaseFirestore.getInstance().collection("users").document(userId)

                    val nameChangeRequestReference =
                        userReference.collection("name_change_requests").document()
                    val currentDate = Calendar.getInstance().time
                    val nextChangeDate = Calendar.getInstance()
                    nextChangeDate.time = currentDate
                    nextChangeDate.add(Calendar.DAY_OF_MONTH, 30)

                    val nameChangeRequestData = hashMapOf(
                        "requestDate" to currentDate,
                        "nextChangeDate" to nextChangeDate.time
                    )

                    nameChangeRequestReference.set(nameChangeRequestData)
                        .addOnSuccessListener {
                            userReference.update("name", newName)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Name updated successfully.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "ou can't change your name for 30 days.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                requireContext(),
                                "Error",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }
                dialog.dismiss()
            }

            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.positive_button_color
                    )
                )
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.negative_button_color
                    )
                )
        }
    }

    private fun changeProfession() {
        binding.changeProfession.setOnClickListener {
            val professions = arrayOf(
                "Your Profession", "Android Developer",
                "Architect",
                "Artificial Intelligence Engineer",
                "Automation Engineer",
                "Back-End Developer",
                "Big Data Analyst",
                "Biotechnology Researcher",
                "Blockchain Developer",
                "Cloud Solutions Architect",
                "Computer Vision Expert",
                "Construction Professional",
                "Cybersecurity Specialist",
                "Data Scientist",
                "Database Administrator",
                "Ecological Engineer",
                "Front-End Developer",
                "Full-Stack Developer",
                "Game Developer",
                "Graphic and Web Designer",
                "Internet of Things (IoT) Specialist",
                "IOS Developer",
                "Machine Learning Engineer",
                "Mobile App Developer (Android & iOS)",
                "Motion Designer",
                "Musician",
                "Network Security Analyst",
                "Painter",
                "Photographer",
                "Quality Assurance (QA) Engineer",
                "Renewable Energy Technologist",
                "Remote Sensing Specialist",
                "Space Technology Engineer",
                "User Experience (UX) Designer",
                "User Interface (UI) Designer",
                "Video Production Specialist",
                "Visual Effects Artist",
                "Web Designer"
            )
            var selectedProfessionIndex = 0

            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Select a Profession")
            builder.setSingleChoiceItems(
                professions,
                selectedProfessionIndex
            ) { dialog, selectedIndex ->
                selectedProfessionIndex = selectedIndex
            }
            builder.setPositiveButton("OK") { dialog, _ ->
                val selectedProfession = professions[selectedProfessionIndex]
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val userReference =
                        FirebaseFirestore.getInstance().collection("users").document(userId)
                    val professionData = hashMapOf("profession" to selectedProfession)

                    userReference.update(professionData as Map<String, Any>)
                        .addOnSuccessListener {
                            Snackbar.make(
                                binding.root,
                                "Profession changed successfully",
                                Snackbar.LENGTH_SHORT
                            )
                        }
                        .addOnFailureListener {
                            Snackbar.make(
                                binding.root, "Failed to change profession", Snackbar.LENGTH_SHORT
                            )
                        }
                }

                dialog.dismiss()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.positive_button_color
                    )
                )
            dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.negative_button_color
                    )
                )
        }
    }

    private fun changeEmail() {

        binding.changeYourMail.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Change your email")

            val input = EditText(requireContext())
            input.hint = "    example@mail.com"
            builder.setView(input)

            builder.setPositiveButton("Save") { dialog, _ ->
                val newEmail = input.text.toString()

                val user = FirebaseAuth.getInstance().currentUser
                user?.updateEmail(newEmail)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {

                            val uid = user.uid
                            val userRef =
                                FirebaseFirestore.getInstance().collection("users").document(uid)
                            userRef.update("email", newEmail)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "Email has been updated.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        requireContext(),
                                        "An error occurred while updating the email.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "An error occurred while updating the email.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.positive_button_color
                    )
                )
            alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                ?.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.negative_button_color
                    )
                )
        }

    }

    private fun changeYourPassword() {
        binding.changeYourPassword.setOnClickListener {
            findNavController().navigate(R.id.forgotPasswordFragment2)
        }
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log out")
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            logoutLogic()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            ?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.logout_positive_button_color
                )
            )
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            ?.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.logout_negative_button_color
                )
            )
    }

    private fun logOutButtonClick() {
        binding.logOutButton.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun logoutLogic() {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
        } else {
            Snackbar.make(
                binding.root, "Unknown error. Try again later", Snackbar.LENGTH_SHORT
            ).show()
        }
    }

}




