package com.bashir.codezy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bashir.codezy.databinding.FragmentStartPageBinding


class StartPage : Fragment() {

    private lateinit var binding: FragmentStartPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartPageBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.butregist.setOnClickListener {
            val action = StartPageDirections.actionStartPageToRegisterMain()
            findNavController().navigate(action)
        }


    }


}