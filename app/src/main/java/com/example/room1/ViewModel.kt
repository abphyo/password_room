package com.example.room1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.room1.room.Dao
import com.example.room1.room.Data
import com.example.room1.room.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ViewModelActivity1(private val dao: Dao): ViewModel() {

    private val _isRegister = MutableStateFlow(false)
    val isRegister: StateFlow<Boolean> get() = _isRegister

    private val _loginStatus = MutableStateFlow(false)
    val loginStatus: StateFlow<Boolean> get() = _loginStatus

    private val _accStatus = MutableStateFlow(false)
    val accStatus: StateFlow<Boolean> get() = _accStatus

    private val _username = MutableStateFlow("")
    val username: StateFlow<String> get() = _username

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> get() = _password

    private val _usernameAsKey = MutableStateFlow("")
    val usernameAsKey: StateFlow<String> get() = _usernameAsKey

    private val _isNameExists = MutableStateFlow(false)
    val isNameExists: StateFlow<Boolean> get() = _isNameExists

    fun registerUser(user: User) {
        viewModelScope.launch {
            dao.insert(user)
            _isRegister.update { true }
        }
    }

    fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            val user = dao.getByUsername(username)
            _accStatus.update { username == user?.username }
            _loginStatus.update { password == user?.password }
        }
    }

    fun memorize(str1: String, str2: String) {
        viewModelScope.launch {
            _username.update { str1 }
            _password.update { str2 }
        }
    }

    fun setKey(username: String) {
        viewModelScope.launch {
            _usernameAsKey.update { username }
        }
    }

    fun checkNameExists(username: String) {
        viewModelScope.launch {
            val count = dao.checkIfNameExists(username)
            _isNameExists.update { count > 0 }
        }
    }

}