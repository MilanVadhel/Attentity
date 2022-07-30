package com.smvs.gkm.data.auth.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_FIRST_NAME
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_LAST_NAME
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_PASSWORD
import com.smvs.gkm.common.utils.ApiKeyUtils.KEY_USERNAME
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponse(
    val token: String,
    val user: User
) : Parcelable

@Parcelize
data class User(
    val id: Int,
    @SerializedName(KEY_USERNAME)
    val username: String,
    @SerializedName(KEY_PASSWORD)
    val password: String,
    val role: String,
    val userDetails: UserDetails? = null,
) : Parcelable

@Parcelize
data class UserDetails(
    val id: Int,
    @SerializedName(KEY_FIRST_NAME)
    val firstName: String,
    @SerializedName(KEY_LAST_NAME)
    val lastName: String,
    val email: String,
    val contact: String,
    val assignedUser: AssignedUser? = null
) : Parcelable

@Parcelize
data class AssignedUser(
    val id: Int,
    @SerializedName(KEY_USERNAME)
    val username: String,
    val userDetails: AssignedUserDetails? = null,
) : Parcelable

@Parcelize
data class AssignedUserDetails(
    @SerializedName(KEY_FIRST_NAME)
    val firstName: String,
    @SerializedName(KEY_LAST_NAME)
    val lastName: String,
    val email: String,
    val contact: String,
) : Parcelable