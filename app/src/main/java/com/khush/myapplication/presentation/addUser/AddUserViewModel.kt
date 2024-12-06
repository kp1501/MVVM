package com.khush.myapplication.presentation.addUser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khush.myapplication.data.local.model.ErrorField
import com.khush.myapplication.data.local.model.ErrorModel
import com.khush.myapplication.data.local.model.UserEntity
import com.khush.myapplication.domain.AppRepository
import com.khush.myapplication.extension.isValidEmail
import com.khush.myapplication.presentation.login.LoginViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    private val insertEventChannel = Channel<InsertEvent>()
    val insertEvent = insertEventChannel.receiveAsFlow()

    fun insertUser(firstName: String, lastName: String, email: String) {
        if (firstName.isBlank()) {
            sendErrorEvent(ErrorModel(ErrorField.FIRST_NAME, "Please enter first name"))
            return
        }
        if (lastName.isBlank()) {
            sendErrorEvent(ErrorModel(ErrorField.LAST_NAME, "Please enter last name"))
            return
        }
        if (email.isBlank()) {
            sendErrorEvent(ErrorModel(ErrorField.EMAIL, "Please enter email"))
            return
        }
        if (!email.isValidEmail()) {
            sendErrorEvent(ErrorModel(ErrorField.EMAIL, "Please enter valid email"))
            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            appRepository.insert(UserEntity(0, firstName, lastName, email))
        }
        insertSuccessful()
    }

    private fun insertSuccessful() = viewModelScope.launch {
        insertEventChannel.send(InsertEvent.InsertSuccessful)
    }

    private fun sendErrorEvent(errorModel: ErrorModel) = viewModelScope.launch {
        insertEventChannel.send(InsertEvent.WrongInput(errorModel))
    }

    sealed class InsertEvent {
        object InsertSuccessful : InsertEvent()
        data class WrongInput(val errorModel: ErrorModel) : InsertEvent()
    }
}