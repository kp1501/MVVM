package com.khush.myapplication.presentation.users

import androidx.lifecycle.ViewModel
import com.khush.myapplication.data.local.model.UserEntity
import com.khush.myapplication.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {

    fun getAllUsers(): List<UserEntity> {
        return appRepository.getAll()
    }

}