package com.smvs.gkm.ui.common.base

import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.enums.NetworkStatus
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private var isFirst = true
    private var _networkStatusLiveEvent: SingleLiveEvent<NetworkStatus> =
        SingleLiveEvent()
    val networkStatusLiveEvent: LiveData<NetworkStatus> get() = _networkStatusLiveEvent

    /**
     * Only can call once per lifecycle
     * @param multipleTimes (OPTIONAL) set it to true to make multiple call capability
     */
    @CallSuper
    open fun loadPage(multipleTimes: Boolean = false): Boolean {
        if (isFirst) {
            isFirst = false
            return true
        }

        return isFirst || multipleTimes
    }

    fun setNetworkStatus(networkStatus: NetworkStatus) {
        viewModelScope.launch {
            _networkStatusLiveEvent.value = networkStatus
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}