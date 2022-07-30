package com.smvs.gkm.ui.main.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.data.main.landing.entity.Option
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LandingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _centerNameLiveEvent: SingleLiveEvent<String> = SingleLiveEvent()
    val centerNameLiveEvent: LiveData<String> get() = _centerNameLiveEvent

    fun getCenterName(){
        viewModelScope.launch {
            _centerNameLiveEvent.value = authRepository.getCenterName()
        }
    }
}