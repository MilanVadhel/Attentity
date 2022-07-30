package com.smvs.gkm.data.sharedprefs

import android.content.SharedPreferences
import com.app.gofood.data.sharedprefs.BaseSharedPreferences
import com.app.gofood.data.sharedprefs.SecurityGuards

class SharedPrefs(
    private val sharedPreferences: SharedPreferences,
    private val securityGuards: SecurityGuards
) : BaseSharedPreferences(securityGuards) {

    var token: String
        set(value) = sharedPreferences.put(TOKEN, value)
        get() = sharedPreferences.get(TOKEN, String::class.java)

    var userName: String
        set(value) = sharedPreferences.put(USER_NAME, value)
        get() = sharedPreferences.get(USER_NAME, String::class.java)

    var password: String
        set(value) = sharedPreferences.put(PASSWORD, value)
        get() = sharedPreferences.get(PASSWORD, String::class.java)

    var firstName: String
        set(value) = sharedPreferences.put(FIRST_NAME, value)
        get() = sharedPreferences.get(FIRST_NAME, String::class.java)

    var centerName: String
        set(value) = sharedPreferences.put(CENTER_NAME, value)
        get() = sharedPreferences.get(CENTER_NAME, String::class.java)

    var lastName: String
        set(value) = sharedPreferences.put(LAST_NAME, value)
        get() = sharedPreferences.get(LAST_NAME, String::class.java)

    var email: String
        set(value) = sharedPreferences.put(EMAIL, value)
        get() = sharedPreferences.get(EMAIL, String::class.java)

    var contact: String
        set(value) = sharedPreferences.put(CONTACT, value)
        get() = sharedPreferences.get(CONTACT, String::class.java)

    var role: String
        set(value) = sharedPreferences.put(ROLE, value)
        get() = sharedPreferences.get(ROLE, String::class.java)

    fun isLoggedIn(): Boolean {
        return token.isNotEmpty()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    fun onErrorCallback(callback: SecurityGuards.ErrorCallback) {
        securityGuards.onErrorCallback(callback)
    }

    companion object {
        internal const val PREFS_NAME = "AppPreferences"
        internal const val TOKEN = "token"
        internal const val USER_NAME = "username"
        internal const val PASSWORD = "password"
        internal const val FIRST_NAME = "firstName"
        internal const val CENTER_NAME = "centerName"
        internal const val LAST_NAME = "lastName"
        internal const val EMAIL = "email"
        internal const val CONTACT = "contact"
        internal const val ROLE = "role"
    }
}
