package com.smvs.gkm.domain.report.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.report.entity.Report

interface ReportRepository {
    suspend fun getReportedMembers() : ApiResponse<ArrayList<Report>>
}