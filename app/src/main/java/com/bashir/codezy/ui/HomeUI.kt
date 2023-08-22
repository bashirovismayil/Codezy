package com.bashir.codezy.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R
import com.bashir.codezy.adapters.PostAdapter
import com.bashir.codezy.data.model.Post
import com.bashir.codezy.databinding.ActivityMainBinding
import com.bashir.codezy.databinding.FragmentHomeUiBinding
import com.bashir.codezy.databinding.FragmentRegisterMainBinding
import com.bashir.codezy.viewmodel.HomeUIViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class HomeUI : Fragment() {


    private lateinit var binding: FragmentHomeUiBinding
    private val viewModel: HomeUIViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeUiBinding.inflate(inflater, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = requireActivity().window
            window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
        }
        return binding.root
        return view


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.postLivedata.observe(viewLifecycleOwner) {
            println(it)
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
            recyclerView.adapter = PostAdapter(it)

            recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            recyclerView.adapter?.notifyDataSetChanged()
        }

    }
}