package com.smvs.gkm.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_IS_LOGIN
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_ROLE
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _isLoginLiveEvent: SingleLiveEvent<HashMap<String,Any>> = SingleLiveEvent()
    val isLoginLiveEvent: LiveData<HashMap<String,Any>> get() = _isLoginLiveEvent

    fun isLogin() {
        viewModelScope.launch {
            _isLoginLiveEvent.value = hashMapOf(
                KEY_IS_LOGIN to authRepository.isLoggedIn(),
                KEY_ROLE to authRepository.role()
            )
        }
    }
}