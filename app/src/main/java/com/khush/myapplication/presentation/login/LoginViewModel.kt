package com.khush.myapplication.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khush.myapplication.data.local.model.ErrorField
import com.khush.myapplication.data.local.model.ErrorModel
import com.khush.myapplication.extension.isPasswordValid
import com.khush.myapplication.extension.isValidEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val loginEventChannel = Channel<LoginEvent>()
    val loginEvent = loginEventChannel.receiveAsFlow()

    fun loginProcess(loginEmail: String, loginPass: String) {
        if (loginEmail.isBlank()) {
            sendErrorEvent(ErrorModel(ErrorField.EMAIL, "Please enter email"))
            return
        }
        if (!loginEmail.isValidEmail()) {
            sendErrorEvent(ErrorModel(ErrorField.EMAIL, "Please enter valid email"))
            return
        }
        if (loginPass.isBlank()) {
            sendErrorEvent(ErrorModel(ErrorField.PASSWORD, "Please enter password"))
            return
        }
        if (!loginPass.isPasswordValid()) {
            sendErrorEvent(ErrorModel((ErrorField.PASSWORD), "Please enter valid password"))
            return
        }
        if (loginEmail != "testapp@google.com") {
            sendErrorEvent(ErrorModel((ErrorField.EMAIL), "Email is wrong!"))
            return
        }
        if (loginPass != "Test@123456") {
            sendErrorEvent(ErrorModel((ErrorField.PASSWORD), "Password is wrong!"))
            return
        }
        navigateToUserListScreen()
    }

    private fun navigateToUserListScreen() = viewModelScope.launch {
        loginEventChannel.send(LoginEvent.NavigateToUserListScreen)
    }

    private fun sendErrorEvent(errorModel: ErrorModel) = viewModelScope.launch {
        loginEventChannel.send(LoginEvent.WrongInput(errorModel))
    }

    sealed class LoginEvent {
        object NavigateToUserListScreen : LoginEvent()
        data class WrongInput(val errorModel: ErrorModel) : LoginEvent()
    }

}