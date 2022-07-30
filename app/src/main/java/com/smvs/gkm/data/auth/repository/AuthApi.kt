package com.smvs.gkm.data.auth.repository

import com.smvs.gkm.data.auth.entity.LoginResponse
import com.smvs.gkm.data.base.ApiResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface AuthApi {

    companion object {
        const val END_POINT_LOGIN = "auth/login"
    }

    @POST(END_POINT_LOGIN)
    suspend fun login(
        @Body hashMap: HashMap<String, Any>
    ): ApiResponse<LoginResponse>
}