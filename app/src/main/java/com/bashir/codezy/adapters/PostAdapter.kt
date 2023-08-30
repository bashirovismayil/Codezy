package com.bashir.codezy.adapters

import android.content.ContentProvider
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bashir.codezy.R
import com.bashir.codezy.data.model.Post
import com.bashir.codezy.ui.HomeUI
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


class PostAdapter(private var posts: List<Post>) : RecyclerView.Adapter<PostViewHolder>() {

    private lateinit var readMoreButton: TextView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        readMoreButton = view.findViewById(R.id.readMoreButton)
        return PostViewHolder(view)
    }


    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = posts[position]
        val userEmail = currentPost.username




        val db = Firebase.firestore
        val usersCollection = db.collection("users")
        usersCollection.whereEqualTo("email", userEmail)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val userDocument = querySnapshot.documents[0]
                    val username = userDocument.getString("name")
                    holder.usernameTextView.text = username
                }
            }



        holder.usernameTextView.text = currentPost.username
        holder.titleTextView.text = posts[position].title
        holder.dateTextView.text = posts[position].date
        holder.contentTextView.text = posts[position].contentText

        readMoreButton.setOnClickListener { view ->
            findNavController(view).navigate(R.id.readMoreFragment)

            val bundle = Bundle()
            bundle.putString("title", currentPost.title)
            bundle.putString("date", currentPost.date)
            bundle.putString("content", currentPost.contentText)

        }
    }

    override fun getItemCount(): Int {
        return posts.size
    }

}



