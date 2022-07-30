package com.smvs.gkm.data.main.report.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.data.base.ApiResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    val id: Int,
    @SerializedName(ApiKeyUtils.KEY_FIRST_NAME)
    val firstName: String,
    @SerializedName(ApiKeyUtils.KEY_LAST_NAME)
    val lastName: String,
    val email: String,
    val contact: String,
    val assignedUser: AssignedUser
) : Parcelable


@Parcelize
data class AssignedUser(
    val id: Int,
    @SerializedName(ApiKeyUtils.KEY_FIRST_NAME)
    val firstName: String,
    @SerializedName(ApiKeyUtils.KEY_LAST_NAME)
    val lastName: String,
    val email: String,
    val contact: String,
) : Parcelable
