package com.smvs.gkm.data.main.attendance.repository

import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance
import com.smvs.gkm.domain.attendance.repository.AttendanceRepository
import javax.inject.Inject

class AttendanceRepositoryImpl @Inject constructor(
    private val attendanceApi: AttendanceApi
) : AttendanceRepository {

    override suspend fun getAssignedMembersAttendances(): ApiResponse<ArrayList<AssignedMemberAttendance>> {
        return attendanceApi.getAssignedMembersAttendances()
    }

    override suspend fun saveAttendance(bodyMap: HashMap<String, Any>): ApiResponse<Unit> {
        return attendanceApi.saveAttendance(bodyMap)
    }
}