package com.smvs.gkm.data.main.attendance.entity

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.smvs.gkm.common.utils.ApiKeyUtils
import kotlinx.parcelize.Parcelize


@Parcelize
data class AssignedMemberAttendance(
    val id: Int,
    @SerializedName(ApiKeyUtils.KEY_FIRST_NAME)
    val firstName: String,
    @SerializedName(ApiKeyUtils.KEY_LAST_NAME)
    val lastName: String,
    val email: String,
    val contact: String,
    val attendances : ArrayList<Attendance>
) : Parcelable


@Parcelize
data class Attendance(
    val present : Boolean,
    val report : Boolean,
    val date : String
): Parcelable