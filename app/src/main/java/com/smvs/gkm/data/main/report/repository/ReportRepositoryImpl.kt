package com.smvs.gkm.data.main.report.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.report.entity.Report
import com.smvs.gkm.domain.report.repository.ReportRepository
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportApi: ReportApi
) : ReportRepository {
    override suspend fun getReportedMembers(): ApiResponse<ArrayList<Report>> {
        return reportApi.getReportedMembers()
    }
}