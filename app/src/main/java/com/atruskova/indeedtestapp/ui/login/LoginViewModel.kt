package com.atruskova.indeedtestapp.ui.login

import androidx.lifecycle.*
import com.atruskova.indeedtestapp.data.network.ResponseState
import kotlinx.coroutines.launch
import com.atruskova.indeedtestapp.R
import com.atruskova.indeedtestapp.data.models.SampleAppUser


class LoginViewModel() : ViewModel() {
    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginButtonEnable = MediatorLiveData<Boolean>()
    val loginButtonEnable: LiveData<Boolean> = _loginButtonEnable

    private val _loginResult: MutableLiveData<LoginResult> = MutableLiveData()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {
        _loginButtonEnable.postValue(false)
        _loginButtonEnable.addSource(loginFormState) {
            _loginButtonEnable.postValue(it.isDataValid)
        }
        _loginButtonEnable.addSource(loginResult) {
            _loginButtonEnable.postValue(true)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value =
                LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value =
                LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    fun login(username: String, password: String) {
        _loginButtonEnable.postValue(false)
        viewModelScope.launch {
            // call server side auth method here
            // now fake successful
            SampleAppUser.username = username
            _loginResult.postValue(LoginResult(true))

        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }


}