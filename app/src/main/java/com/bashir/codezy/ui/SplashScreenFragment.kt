package com.bashir.codezy.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentSplashScreenBinding
import com.bashir.codezy.databinding.FragmentUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenFragment : Fragment() {
    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserStatusAndNavigate()
        }, 2000)
    }

    private fun checkUserStatusAndNavigate() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {


        } else {

        }
    }
}