package com.smvs.gkm.data.main.attendance.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AttendanceApi {

    companion object {
        const val END_POINT_GET_ASSIGNED_MEMBERS_ATTENDANCE = "assigned-members-attendance"
        const val END_POINT_SAVE_ATTENDANCE = "attendance"
    }

    @GET(END_POINT_GET_ASSIGNED_MEMBERS_ATTENDANCE)
    suspend fun getAssignedMembersAttendances(): ApiResponse<ArrayList<AssignedMemberAttendance>>

    @POST(END_POINT_SAVE_ATTENDANCE)
    suspend fun saveAttendance(
        @Body hashMap: HashMap<String, Any>
    ): ApiResponse<Unit>
}