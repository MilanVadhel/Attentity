package com.smvs.gkm.data.auth.repository

import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_FIRST_NAME
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_LAST_NAME
import com.smvs.gkm.data.sharedprefs.SharedPrefs
import com.smvs.gkm.data.auth.entity.LoginResponse
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val sharedPrefs: SharedPrefs
) : AuthRepository {

    override suspend fun login(bodyMap: HashMap<String, Any>): ApiResponse<LoginResponse> {
        return authApi.login(bodyMap)
    }

    override suspend fun saveUserInfo(user: User, token: String, centerName: String) {
        sharedPrefs.apply {
            this.token = token
            userName = user.username
            password = user.password
            role = user.role
            firstName = user.userDetails?.firstName!!
            lastName = user.userDetails.lastName
            email = user.userDetails.email
            contact = user.userDetails.contact
            this.centerName = centerName
        }
    }

    override suspend fun getUserInfo(): HashMap<String, String> {
        return hashMapOf(
            KEY_FIRST_NAME to sharedPrefs.firstName,
            KEY_LAST_NAME to sharedPrefs.lastName
        )
    }

    override suspend fun getToken(): String {
        return sharedPrefs.token
    }

    override suspend fun getCenterName(): String {
        return sharedPrefs.centerName
    }

    override suspend fun getUserName(): String {
        return sharedPrefs.userName
    }

    override suspend fun isLoggedIn(): Boolean {
        return sharedPrefs.isLoggedIn()
    }

    override suspend fun role(): String {
        return sharedPrefs.role
    }

    override suspend fun logout() {
        sharedPrefs.clear()
    }
}