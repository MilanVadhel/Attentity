package com.smvs.gkm.data.main.report.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.report.entity.Report
import retrofit2.http.GET

interface ReportApi {

    companion object {
        const val END_POINT_REPORTED_MEMBERS = "reported-members"
    }

    @GET(END_POINT_REPORTED_MEMBERS)
    suspend fun getReportedMembers(): ApiResponse<ArrayList<Report>>
}