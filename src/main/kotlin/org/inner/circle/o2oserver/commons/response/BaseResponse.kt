package org.inner.circle.o2oserver.commons.response

data class BaseResponse(
    val response: Any,
    val statusCode: Int,
    val msg: String,
) {
    companion object {
        fun success(response: Any): BaseResponse {
            return BaseResponse(
                response = response,
                statusCode = 200,
                msg = "success",
            )
        }

        fun error(response: Any, statusCode: Int): BaseResponse {
            return BaseResponse(
                response = response,
                statusCode = statusCode,
                msg = "fail",
            )
        }
    }
}
