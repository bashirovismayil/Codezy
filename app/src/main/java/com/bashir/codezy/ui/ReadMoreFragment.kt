package com.bashir.codezy.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.bashir.codezy.R
import com.bashir.codezy.databinding.FragmentLikedPageBinding
import com.bashir.codezy.databinding.FragmentReadMoreBinding

class ReadMoreFragment : Fragment() {

    private lateinit var binding: FragmentReadMoreBinding
    private lateinit var back_button: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReadMoreBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        return binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        back_button = binding.backButton

        back_button.setOnClickListener {
            Navigation.findNavController(back_button).popBackStack()
        }


        val bundle = arguments?.getBundle("bundle")


        val title = bundle?.getString("title")
        val date = bundle?.getString("date")
        val content = bundle?.getString("content")

        binding.readMoreTitle.text = title
        binding.readMoreDate.text = date
        binding.readMorePostContent.text = content
    }
}