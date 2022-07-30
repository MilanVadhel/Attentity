package com.smvs.gkm.common.exception

import com.smvs.gkm.data.base.MetaResponse
import java.io.IOException

open class AppHttpException(private val response: MetaResponse) : IOException() {

    override val message: String?
        get() = response.error

    class ServerException(response: MetaResponse) : AppHttpException(response)
    class UnauthorizedException(response: MetaResponse) : AppHttpException(response)
    class UnProcessableException(response: MetaResponse) : AppHttpException(response)
    class UnExpectedException(response: MetaResponse) : AppHttpException(response)
    class ClientException(response: MetaResponse) : AppHttpException(response)
}
