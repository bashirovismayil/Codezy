package com.bashir.codezy.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bashir.codezy.R
import com.bashir.codezy.data.model.Post
import com.bashir.codezy.databinding.FragmentAddPostPageBinding
import com.bashir.codezy.databinding.FragmentRegisterMainBinding
import com.bashir.codezy.viewmodel.AddPostUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class AddPostPage : Fragment() {

    private lateinit var binding: FragmentAddPostPageBinding
    private lateinit var title: EditText
    private lateinit var text: EditText
    private val viewModel: AddPostUIViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostPageBinding.inflate(inflater, container, false)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.codezy_light_green)
        }
        return binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        title = view.findViewById(R.id.AddPostTitle)
        text = view.findViewById(R.id.AddPostText)

        view.findViewById<Button>(R.id.share_button).setOnClickListener {

            val post = Post(
                title = title.text.toString(),
                contentText = text.text.toString(),
                date = Date().toString()
            )

            viewModel.insertPostFirebase(post)


            return@setOnClickListener
        }
    }
}