package com.bashir.codezy.domain.repository

import androidx.lifecycle.LiveData
import com.bashir.codezy.common.FirebaseAuthResult
import com.bashir.codezy.data.dao.UserDao
import com.bashir.codezy.data.model.User
import com.bashir.codezy.data.source.FirebaseAuthDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserRepository(
    private val firebaseAuthDataSource: FirebaseAuthDataSource,
    private val userDao: UserDao
) {


    fun registerUser(email: String, password: String, callback: (FirebaseAuthResult) -> Unit) {
        firebaseAuthDataSource.registerUser(email, password) { result ->
            when (result) {
                is FirebaseAuthResult.Success -> {
                    // Firebase auth succes olarsa, rooma add etmek
                    val user = result.user
                    CoroutineScope(Dispatchers.IO).launch {
                        userDao.insertUser(user)
                    }
                    callback(result)

                }

                is FirebaseAuthResult.Error -> callback(result)
            }
        }
    }


    fun loginUser(email: String, password: String, callback: (FirebaseAuthResult) -> Unit) {
        firebaseAuthDataSource.loginUser(email, password) { result ->
            if (result is FirebaseAuthResult.Success) {

                val user = result.user
                CoroutineScope(Dispatchers.IO).launch {
                    userDao.insertUser(user)
                }
            }
            callback(result)
        }
    }

    fun getUser(): LiveData<User> {
        return userDao.getUser()
    }
}
