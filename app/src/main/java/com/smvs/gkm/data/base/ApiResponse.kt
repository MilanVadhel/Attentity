package com.smvs.gkm.data.base

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smvs.gkm.common.utils.ApiKeyUtils
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Parcelize
open class ApiResponse<T> : Parcelable {
    @SerializedName("data")
    var data: T? = null

    @SerializedName("meta")
    lateinit var meta: MetaResponse
}

@Parcelize
data class MetaResponse(
    @SerializedName("error")
    val error: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
) : Parcelable

