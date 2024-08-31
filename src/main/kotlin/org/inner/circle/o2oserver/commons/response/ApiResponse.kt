package org.inner.circle.o2oserver.commons.response

data class ApiResponse<T> private constructor(
    val data: T?,
    val message: String?,
    val status: String
) {
    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(data, null, "success")
        }

        fun <T> error(message: String): ApiResponse<T> {
            return ApiResponse(null, message, "fail")
        }
    }
}
