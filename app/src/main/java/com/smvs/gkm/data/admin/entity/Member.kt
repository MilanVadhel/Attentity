package com.smvs.gkm.data.admin.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smvs.gkm.common.utils.ApiKeyUtils

@kotlinx.parcelize.Parcelize
data class Member(
    @SerializedName(ApiKeyUtils.KEY_FIRST_NAME)
    val firstname: String,
    @SerializedName(ApiKeyUtils.KEY_LAST_NAME)
    val lastname: String,
    val email: String,
    val contact: String,
    val assignTo: Int
) : Parcelable
