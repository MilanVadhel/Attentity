package com.smvs.gkm.ui.lrf.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.setError
import com.smvs.gkm.common.extensions.setLoading
import com.smvs.gkm.common.extensions.setSuccess
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import com.smvs.gkm.ui.lrf.login.loginenum.LoginValidation
import com.smvs.gkm.ui.lrf.login.loginenum.Role
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.NullPointerException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _loginValidationLiveEvent: SingleLiveEvent<LoginValidation> = SingleLiveEvent()
    val loginValidationLiveEvent: LiveData<LoginValidation> get() = _loginValidationLiveEvent

    private val _loginLiveEvent: SingleLiveEvent<UiState<String>> = SingleLiveEvent()
    val loginLiveEvent: LiveData<UiState<String>> get() = _loginLiveEvent

    fun login(userName: String, password: String,centerName : String) {
        when {
            userName.isNullOrEmpty() -> {
                _loginValidationLiveEvent.value = LoginValidation.ID_EMPTY
            }
            password.isNullOrEmpty() -> {
                _loginValidationLiveEvent.value = LoginValidation.PASSWORD_EMPTY
            }
            else -> {
                loginUser(userName, password,centerName)
            }
        }
    }

    private fun loginUser(userName: String, password: String,centerName : String) {
        viewModelScope.launch {
            try {
                _loginLiveEvent.setLoading()
                val apiResponse = authRepository.login(
                    hashMapOf(
                        ApiKeyUtils.KEY_USERNAME to userName,
                        ApiKeyUtils.KEY_PASSWORD to password,
                    )
                )
                apiResponse.data?.let { loginResponse ->
                    _loginLiveEvent.setSuccess(loginResponse.user.role)
                    authRepository.saveUserInfo(
                        user = loginResponse.user,
                        token = loginResponse.token,
                        centerName = centerName
                    )
                }
            } catch (e: Exception) {
                if(e !is NullPointerException){
                    _loginLiveEvent.setError(e)
                }
            }
        }
    }
}