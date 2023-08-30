package com.bashir.codezy.viewmodel

import androidx.lifecycle.ViewModel
import com.bashir.codezy.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeUiViewModel @Inject constructor (
//    val posts: MutableList<Post>
) : ViewModel()
