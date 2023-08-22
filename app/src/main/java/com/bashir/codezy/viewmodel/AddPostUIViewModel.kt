package com.bashir.codezy.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashir.codezy.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import com.google.type.Date
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddPostUIViewModel @Inject constructor(
    val firestore: FirebaseFirestore
) : ViewModel() {

    fun insertPostFirebase(post: Post) {
        viewModelScope.launch {
            val postMap = hashMapOf<String, Any>()
            postMap["title"] = post.title
            postMap["contentText"] = post.contentText
            postMap["date"] = com.google.firebase.Timestamp.now()

            firestore.collection("Posts").document(post.title).set(postMap).addOnSuccessListener {

            }.addOnFailureListener {

            }
        }
    }
}