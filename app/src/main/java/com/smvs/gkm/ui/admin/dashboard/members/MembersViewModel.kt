package com.smvs.gkm.ui.admin.dashboard.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.setEmpty
import com.smvs.gkm.common.extensions.setError
import com.smvs.gkm.common.extensions.setLoading
import com.smvs.gkm.common.extensions.setSuccess
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.domain.admin.repository.AdminRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MembersViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : BaseViewModel() {

    private val _membersLiveEvent: SingleLiveEvent<UiState<ArrayList<UserDetails>>> =
        SingleLiveEvent()
    val membersLiveEvent: LiveData<UiState<ArrayList<UserDetails>>> get() = _membersLiveEvent

    private val _deleteMemberLiveEvent: SingleLiveEvent<UiState<Unit>> =
        SingleLiveEvent()
    val deleteMemberLiveEvent: LiveData<UiState<Unit>> get() = _deleteMemberLiveEvent

    fun getMembers() {
        viewModelScope.launch {
            try {
                _membersLiveEvent.setLoading()
                val apiResponse = adminRepository.getMembers()
                val membersList = apiResponse.data
                if (membersList.isNullOrEmpty()) {
                    _membersLiveEvent.setEmpty()
                } else {
                    _membersLiveEvent.setSuccess(membersList)
                }
            } catch (e: Exception) {
                _membersLiveEvent.setError(e)
            }
        }
    }

    fun deleteMember(memberId: Int) {
        viewModelScope.launch {
            try {
                _deleteMemberLiveEvent.setLoading()
                val apiResponse = adminRepository.deleteMember(memberId)
                val membersList = apiResponse.data
                _deleteMemberLiveEvent.setSuccess(Unit)
            } catch (e: Exception) {
                _deleteMemberLiveEvent.setError(e)
            }
        }
    }
}