package com.smvs.gkm.common.interceptor

import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.data.sharedprefs.SharedPrefs
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AccessTokenInterceptor @Inject constructor(
    private val sharedPrefs: SharedPrefs
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        setAuthHeader(builder, sharedPrefs.token)
        val newRequest = builder.build()
        return chain.proceed(newRequest)
    }

    /**
     * set header
     */
    private fun setAuthHeader(builder: Request.Builder, token: String?) {
        token?.let {
            builder.header(
                ApiKeyUtils.KEY_AUTHORIZATION,
                "${ApiKeyUtils.TOKEN_PREFIX} $it"
            )
        }
    }
}