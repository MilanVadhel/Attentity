package com.smvs.gkm.data.admin.repository

import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.domain.admin.repository.AdminRepository
import javax.inject.Inject

class AdminRepositoryImpl @Inject constructor(
    private val adminApi: AdminApi
) : AdminRepository {

    override suspend fun getUsers(): ApiResponse<ArrayList<User>> {
        return adminApi.getUsers()
    }

    override suspend fun getMembers(): ApiResponse<ArrayList<UserDetails>> {
        return adminApi.getMembers()
    }

    override suspend fun addMember(hashMap: HashMap<String, Any>): ApiResponse<Unit> {
        return adminApi.addMember(hashMap)
    }

    override suspend fun editMember(
        memberId: Int,
        hashMap: HashMap<String, Any>
    ): ApiResponse<Unit> {
        return adminApi.editMember(memberId, hashMap)
    }

    override suspend fun deleteMember(memberId: Int): ApiResponse<Unit> {
        return adminApi.deleteMember(memberId)
    }
}