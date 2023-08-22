package com.bashir.codezy.common

import com.bashir.codezy.data.model.User

sealed class FirebaseAuthResult {
    data class Success(val user: User) : FirebaseAuthResult()
    data class Error(val exception: Exception?) : FirebaseAuthResult()
}