package org.inner.circle.o2oserver.commons.response

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.inner.circle.o2oserver.commons.config.BaseResponseSerializer

@JsonSerialize(using = BaseResponseSerializer::class)
data class BaseResponse<T>(
    @JsonProperty("response")
    val response: T? = null,
    @JsonProperty("statusCode")
    val statusCode: Int = 200,
    @JsonProperty("msg")
    val msg: String = "",
) {
    companion object {
        fun <T> success(response: T): BaseResponse<T> = BaseResponse(response, 200, "success")

        fun <T> error(message: T, statusCode: Int): BaseResponse<T> = BaseResponse(message, statusCode, "fail")
    }
}
