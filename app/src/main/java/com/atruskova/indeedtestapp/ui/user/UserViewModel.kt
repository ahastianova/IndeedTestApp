package com.atruskova.indeedtestapp.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atruskova.indeedtestapp.data.models.SampleAppUser

class UserViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = SampleAppUser.username
    }
    val text: LiveData<String> = _text
}