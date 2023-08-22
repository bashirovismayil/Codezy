package com.bashir.codezy.data.source

import com.bashir.codezy.common.FirebaseAuthResult
import com.bashir.codezy.data.model.User
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthDataSource(
    private val firebaseAuth: FirebaseAuth
) {

    fun registerUser(email: String, password: String, callback: (FirebaseAuthResult) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    firebaseUser?.let {
                        val user = User(it.uid, it.email ?: "", "")
                        callback(FirebaseAuthResult.Success(user))
                    }
                } else {
                    callback(FirebaseAuthResult.Error(result.exception))
                }
            }

    }

    fun loginUser(email: String, password: String, callback: (FirebaseAuthResult) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { result ->
                if (result.isSuccessful) {
                    val firebaseUser = firebaseAuth.currentUser
                    firebaseUser?.let {
                        val user = User(it.uid, it.email ?: "", "")
                        callback(FirebaseAuthResult.Success(user))
                    }
                } else {
                    callback(FirebaseAuthResult.Error(result.exception))
                }
            }
    }
}




