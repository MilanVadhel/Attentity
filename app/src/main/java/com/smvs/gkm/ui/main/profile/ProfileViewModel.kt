package com.smvs.gkm.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}