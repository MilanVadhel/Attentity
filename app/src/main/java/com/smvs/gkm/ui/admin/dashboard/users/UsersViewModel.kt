package com.smvs.gkm.ui.admin.dashboard.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.setEmpty
import com.smvs.gkm.common.extensions.setError
import com.smvs.gkm.common.extensions.setLoading
import com.smvs.gkm.common.extensions.setSuccess
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.domain.admin.repository.AdminRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val adminRepository: AdminRepository
)  : BaseViewModel(){

    private val _usersLiveEvent: SingleLiveEvent<UiState<ArrayList<User>>> =
        SingleLiveEvent()
    val usersLiveEvent: LiveData<UiState<ArrayList<User>>> get() = _usersLiveEvent

    fun getUsers() {
        viewModelScope.launch {
            try {
                _usersLiveEvent.setLoading()
                val apiResponse = adminRepository.getUsers()
                val usersList = apiResponse.data
                if (usersList.isNullOrEmpty()) {
                    _usersLiveEvent.setEmpty()
                } else {
                    _usersLiveEvent.setSuccess(usersList)
                }
            } catch (e: Exception) {
                _usersLiveEvent.setError(e)
            }
        }
    }
}