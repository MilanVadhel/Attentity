package com.smvs.gkm.domain.admin.repository

import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.data.base.ApiResponse

interface AdminRepository {

    suspend fun getUsers() : ApiResponse<ArrayList<User>>

    suspend fun getMembers() : ApiResponse<ArrayList<UserDetails>>

    suspend fun addMember(
        hashMap: HashMap<String, Any>
    ): ApiResponse<Unit>

    suspend fun editMember(
        memberId: Int,
        hashMap: HashMap<String, Any>
    ): ApiResponse<Unit>

    suspend fun deleteMember(
        memberId: Int,
    ): ApiResponse<Unit>
}