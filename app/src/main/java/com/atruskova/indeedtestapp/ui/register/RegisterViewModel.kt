package com.atruskova.indeedtestapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atruskova.indeedtestapp.data.repository.RegisterRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    fun decodeString(from: String?) {
        viewModelScope.launch {
            _registerState.postValue(RegisterState.loading())
            val result = registerRepository.decodeCredentialsFromBase64(from)
            if (result.successful) {
                _registerState.postValue(RegisterState.success())
            } else {
                _registerState.postValue(RegisterState.error(result.errorMessage))
            }
        }

    }
}