package com.smvs.gkm.ui.main.attendance

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.setEmpty
import com.smvs.gkm.common.extensions.setError
import com.smvs.gkm.common.extensions.setLoading
import com.smvs.gkm.common.extensions.setSuccess
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance
import com.smvs.gkm.domain.attendance.repository.AttendanceRepository
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AttendanceViewModel @Inject constructor(
    private val attendanceRepository: AttendanceRepository,
    private val authRepository: AuthRepository
) : BaseViewModel() {

    private val _assignedMembersAttendanceLiveEvent: SingleLiveEvent<UiState<ArrayList<AssignedMemberAttendance>>> =
        SingleLiveEvent()
    val assignedMembersAttendanceLiveEvent: LiveData<UiState<ArrayList<AssignedMemberAttendance>>> get() = _assignedMembersAttendanceLiveEvent

    private val _saveAttendanceLiveEvent: SingleLiveEvent<UiState<String>> =
        SingleLiveEvent()
    val saveAttendanceLiveEvent: LiveData<UiState<String>> get() = _saveAttendanceLiveEvent

    private val _userInfoLiveEvent: SingleLiveEvent<HashMap<String, String>> =
        SingleLiveEvent()
    val userInfoLiveEvent: LiveData<HashMap<String, String>> get() = _userInfoLiveEvent

    fun getUserInfo() {
        viewModelScope.launch {
            _userInfoLiveEvent.value = authRepository.getUserInfo()
        }
    }

    fun getAssignedMembersAttendances() {
        viewModelScope.launch {
            try {
                _assignedMembersAttendanceLiveEvent.setLoading()
                val apiResponse = attendanceRepository.getAssignedMembersAttendances()
                val assignedMembersAttendanceList = apiResponse.data
                if (assignedMembersAttendanceList.isNullOrEmpty()) {
                    _assignedMembersAttendanceLiveEvent.setEmpty()
                } else {
                    _assignedMembersAttendanceLiveEvent.setSuccess(assignedMembersAttendanceList)
                }
            } catch (e: Exception) {
                _assignedMembersAttendanceLiveEvent.setError(e)
            }
        }
    }

    fun saveAttendance(memberId: Int, present: Boolean, report: Boolean) {
        viewModelScope.launch {
            try {
                _saveAttendanceLiveEvent.setLoading()
                val apiResponse = attendanceRepository.saveAttendance(
                    hashMapOf(
                        ApiKeyUtils.KEY_MEMBER_ID to memberId,
                        ApiKeyUtils.KEY_PRESENT to present,
                        ApiKeyUtils.KEY_REPORT to report
                    )
                )
                _saveAttendanceLiveEvent.setSuccess(apiResponse.meta.error)
            } catch (e: Exception) {
                _assignedMembersAttendanceLiveEvent.setError(e)
            }
        }
    }
}