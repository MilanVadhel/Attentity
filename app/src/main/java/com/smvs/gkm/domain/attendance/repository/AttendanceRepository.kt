package com.smvs.gkm.domain.attendance.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance

interface AttendanceRepository {
    suspend fun getAssignedMembersAttendances(): ApiResponse<ArrayList<AssignedMemberAttendance>>
    suspend fun saveAttendance(bodyMap: HashMap<String, Any>): ApiResponse<Unit>
}