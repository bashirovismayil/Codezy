package com.bashir.codezy.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bashir.codezy.data.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeUIViewModel @Inject constructor(val firebaseFirestore: FirebaseFirestore) : ViewModel() {
    val postLivedata = MutableLiveData<List<Post>>()

    init {

        getPosts()

    }

    fun getPosts() {

        viewModelScope.launch {

            val list = mutableListOf<Post>()

            firebaseFirestore.collection("Posts").addSnapshotListener { value, error ->
                if (value != null && !value.isEmpty) {
                    val docs = value.documents
                    list.clear()

                    for (d in docs) {
                        val title = d["title"].toString()
                        val contentText = d["contentText"].toString()
                        val date = d["date"].toString()

                        val post = Post(title, contentText, date)
                        list.add(post)

                    }

                    postLivedata.value = list


                }
            }

        }

    }

}