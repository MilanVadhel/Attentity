package com.smvs.gkm.common.interceptor

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import com.smvs.gkm.common.exception.AppHttpException
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.data.base.ApiResponse
import com.smvs.gkm.data.base.MetaResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    companion object {
        const val BELOW_100MS = "BELOW 100 ms"
        const val BETWEEN_100_500MS = "BETWEEN 100 - 500 ms"
        const val BETWEEN_500_1000MS = "BETWEEN 500 - 1000 ms"
        const val ABOVE_1000MS = "ABOVE 1000 ms"

        const val SHORT_TIME: Long = 100
        const val MEDIUM_TIME: Long = 500
        const val LONG_TIME: Long = 1000

        const val PREFIX_ERROR = "%s | %s"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = getRequest(chain.request())
        val startNs = System.nanoTime()
        return handleResponse(chain.proceed(builder.build()), startNs)
    }

    private fun getRequest(request: Request): Request.Builder = request.newBuilder()

    private fun handleResponse(response: Response, startNs: Long): Response {
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        val isBelow100: (Long) -> Boolean = { it < SHORT_TIME }
        val isBetween100And500: (Long) -> Boolean = { it in SHORT_TIME..MEDIUM_TIME }
        val isBetween500And1000: (Long) -> Boolean = { it in MEDIUM_TIME..LONG_TIME }

        val apiTime = when {
            isBelow100(tookMs) -> BELOW_100MS
            isBetween100And500(tookMs) -> BETWEEN_100_500MS
            isBetween500And1000(tookMs) -> BETWEEN_500_1000MS
            else -> ABOVE_1000MS
        }

        return when (response.code) {
            in AppHttpStatus.SUCCESS.codes -> response
            in AppHttpStatus.UN_AUTHORIZED.codes -> {
                sendUnAuthorizedBroadcast()
                val body = response.peekBody(Long.MAX_VALUE)
                parseErrorResponse(body).run {
                    val unAuthorizedException = AppHttpException.UnauthorizedException(this)
                    throw unAuthorizedException
                }
            }

            in AppHttpStatus.UN_PROCESSABLE_ENTITY.codes -> {
                val body = response.peekBody(Long.MAX_VALUE)
                parseErrorResponse(body).run {
                    val unProcessableException = AppHttpException.UnProcessableException(this)
                    throw unProcessableException
                }
            }

            in AppHttpStatus.CLIENT_ERRORS.codes -> {
                val body = response.peekBody(Long.MAX_VALUE)
                parseErrorResponse(body).run {
                    val clientException = AppHttpException.ClientException(this)
                    throw clientException
                }
            }

            in AppHttpStatus.SERVER_ERRORS.codes -> {
                val body = response.peekBody(Long.MAX_VALUE)
                parseErrorResponse(body).run {
                    val serverException = AppHttpException.ServerException(this)
                    throw serverException
                }
            }
            else -> throw getUnExpectedException()
        }
    }

    private fun parseErrorResponse(body: ResponseBody): MetaResponse {
        try {
            return Gson().fromJson(body.string(), ApiResponse::class.java).meta
        } catch (e: Exception) {
            Timber.e("parseErrorResponse >> $e")
            throw getUnExpectedException()
        }
    }

    private fun sendUnAuthorizedBroadcast() {
        val intent = Intent(ApiKeyUtils.UN_AUTHORIZED_LOGOUT)
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
    }

    private fun getUnExpectedException(): AppHttpException.UnExpectedException {
        return AppHttpException.UnExpectedException(
            MetaResponse(
                error = "UnExpectedException",
                status = 0,
                message = ""
            )
        )
    }
}
