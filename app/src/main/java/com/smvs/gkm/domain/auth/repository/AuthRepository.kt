package com.smvs.gkm.domain.auth.repository

import com.smvs.gkm.data.auth.entity.LoginResponse
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.base.ApiResponse

interface AuthRepository {
    suspend fun login(bodyMap : HashMap<String,Any>) : ApiResponse<LoginResponse>
    suspend fun saveUserInfo(user: User, token : String,centerName : String)
    suspend fun getUserInfo() : HashMap<String,String>
    suspend fun getToken() : String
    suspend fun getCenterName() : String
    suspend fun getUserName() : String
    suspend fun isLoggedIn() : Boolean
    suspend fun role() : String
    suspend fun logout()
}