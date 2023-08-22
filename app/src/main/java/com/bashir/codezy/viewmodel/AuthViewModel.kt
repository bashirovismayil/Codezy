package com.bashir.codezy.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bashir.codezy.common.FirebaseAuthResult
import com.bashir.codezy.data.model.User
import com.bashir.codezy.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.security.auth.callback.Callback

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    fun registerUser(email: String, password: String,  callback: () -> Unit) {
        userRepository.registerUser(email, password) { result ->
            if (result is FirebaseAuthResult.Success) {
                _user.value = result.user

                callback()

            }
        }
    }

    fun loginUser(email: String, password: String,  callback: () -> Unit) {
        userRepository.loginUser(email, password) { result ->
            if (result is FirebaseAuthResult.Success) {
                _user.value = result.user

                callback()

            }
        }
    }
}
