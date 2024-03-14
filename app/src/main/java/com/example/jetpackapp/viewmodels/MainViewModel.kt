package com.example.jetpackapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Hello, Jetpack and Kotlin!"
    }
    val text: LiveData<String> = _text
}
