package com.example.mutablestateflow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class MyViewModel : ViewModel() {
    private val _state = MutableStateFlow<StateController>(StateController.Empty)

    val state: StateFlow<StateController> = _state


    fun login(userName : String, password : String)= viewModelScope.launch {
        _state.value = StateController.Loading
        delay(3000)

        if(userName == "amit" && password == "1234"){
            _state.value = StateController.Success("Success")
        }else{
            _state.value = StateController.Error("Wrong Info")
        }
    }



    sealed class StateController {
        object Loading : StateController()

        object Empty : StateController()

        data class Error(val message: String) : StateController()

        data class Success(val data: String) : StateController()

    }
}