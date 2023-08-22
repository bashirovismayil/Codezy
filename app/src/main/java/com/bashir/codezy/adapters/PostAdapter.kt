package com.bashir.codezy.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R
import com.bashir.codezy.data.model.Post
import com.bashir.codezy.ui.HomeUI
import org.w3c.dom.Text

class PostAdapter(private val posts: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {

    private lateinit var readMoreButton: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        readMoreButton = view.findViewById(R.id.readMoreButton)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {

        holder.titleTextView.text = posts[position].title
        holder.dateTextView.text = posts[position].date.toString()
        holder.contentTextView.text = posts[position].contentText

        readMoreButton.setOnClickListener { view ->
            Navigation.findNavController(view).navigate(R.id.readMoreFragment)

            val currentPost = posts[position]
            val bundle = Bundle()
            bundle.putString("title", currentPost.title)
            bundle.putString("date", currentPost.date.toString())
            bundle.putString("content", currentPost.contentText)

//            val action = ReadMoreFragmentDirections.actionReadMoreFragmentToReadMoreFragment(currentPost)
//
//
//            Navigation.findNavController(view).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }
}

