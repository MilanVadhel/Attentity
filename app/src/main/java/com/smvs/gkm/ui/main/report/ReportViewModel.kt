package com.smvs.gkm.ui.main.report

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.setEmpty
import com.smvs.gkm.common.extensions.setError
import com.smvs.gkm.common.extensions.setLoading
import com.smvs.gkm.common.extensions.setSuccess
import com.smvs.gkm.data.main.report.entity.Report
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.domain.report.repository.ReportRepository
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val reportRepository: ReportRepository
) : BaseViewModel() {

    private val _reportsLiveEvent: SingleLiveEvent<UiState<ArrayList<Report>>> = SingleLiveEvent()
    val reportsLiveEvent: LiveData<UiState<ArrayList<Report>>> get() = _reportsLiveEvent

    fun getReports() {
        viewModelScope.launch {
            try {
                _reportsLiveEvent.setLoading()
                val apiResponse = reportRepository.getReportedMembers()
                val reportedMembersList = apiResponse.data
                if(reportedMembersList.isNullOrEmpty()){
                    _reportsLiveEvent.setEmpty()
                }else {
                    _reportsLiveEvent.setSuccess(reportedMembersList)
                }
            } catch (e: Exception) {
                _reportsLiveEvent.setError(e)
            }
        }
    }
}