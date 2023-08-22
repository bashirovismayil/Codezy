package com.bashir.codezy.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.animation.Animation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentForgotPasswordBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {
    private lateinit var binding: FragmentForgotPasswordBinding
    private lateinit var emailEditText: EditText
    private lateinit var animationView: LottieAnimationView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }

        return binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        emailEditText = view.findViewById(R.id.sendEmailText)

        val backButton = view.findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            Navigation.findNavController(backButton).popBackStack()
        }

        view.findViewById<Button>(R.id.send_reset_email_button).setOnClickListener {
            val email = emailEditText.text.toString()
            forgotPassword(email)
        }

        animationView = view.findViewById(R.id.animation_view)
        animationView.setAnimation(R.raw.send_mail_animation)
        animationView.repeatCount = Animation.INFINITE
        animationView.playAnimation()


    }

    private fun forgotPassword(email: String) {

        if (email.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Please enter a valid email address.",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val send_reset_email_button = view?.findViewById<Button>(R.id.send_reset_email_button)
        send_reset_email_button?.isEnabled = false
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener(OnCompleteListener { task ->
                send_reset_email_button?.isEnabled = true
                if (task.isSuccessful) {
                    Toast.makeText(
                        requireContext(),
                        "Sent. Please check your email",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message ?: "An unknown error occurred.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}