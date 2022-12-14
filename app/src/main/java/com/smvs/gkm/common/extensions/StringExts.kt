package com.smvs.gkm.common.extensions

import androidx.core.util.PatternsCompat
import com.smvs.gkm.common.utils.Constants.HTTP
import com.smvs.gkm.common.utils.Constants.HTTPS
import java.util.*

fun String.append(target: String): String {
    return StringBuilder(this).append(target).toString()
}

fun String?.checkNotEmpty(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}

fun String?.isValidEmail(): Boolean {
    return if (checkNotEmpty()) {
        this?.let {
            return PatternsCompat.EMAIL_ADDRESS.matcher(this).matches()
        }
        false
    } else {
        false
    }
}

fun String?.isValidOtp(): Boolean {
    return this?.length?.let {
        it >= 4
    } == true
}

fun String?.isNotValidMobileNumber(): Boolean {
    return if (checkNotEmpty()) {
        this?.let {
            return isMinLengthOfMobile(this)
        }
        false
    } else {
        false
    }
}

private fun isMinLengthOfMobile(field: String): Boolean {
    val isValid = field.checkNotEmpty()
    return if (!isValid) {
        false
    } else {
        return field.length != 10
    }
}

private fun isMinLengthOfOTP(field: String): Boolean {
    val isValid = field.checkNotEmpty()
    return if (!isValid) {
        false
    } else {
        return field.length != 4
    }
}


fun String?.isRequiredField(): Boolean {
    return this != null && isNotEmpty() && isNotBlank()
}

fun String?.doesNewConfirmPasswordMatch(confirmPassword: String): Boolean {
    return this.equals(confirmPassword)
}

fun String.isUrl(): Boolean {
    return this.lowercase(Locale.getDefault()).contains(HTTP) ||
            this.lowercase(Locale.getDefault()).contains(HTTPS)
}






