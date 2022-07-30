package com.smvs.gkm.data.main.landing.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smvs.gkm.R

enum class Option(@DrawableRes val icon : Int,@StringRes val label : Int) {
    ATTENDANCE(R.drawable.ic_attendance,R.string.text_attendance),
    VIEW_REPORT(R.drawable.ic_report,R.string.text_view_report),
    PROFILE(R.drawable.ic_user_focused,R.string.text_profile),
    ADD_MEMBER(R.drawable.ic_add_member,R.string.text_add_member),
    VIEW_MEMBER(R.drawable.ic_view_member,R.string.text_view_member),
}