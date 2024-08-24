package org.inner.circle.o2oserver.commons.exception

import org.springframework.http.HttpStatusCode
import org.springframework.web.server.ResponseStatusException

class Exceptions {
    sealed class SetExceptionHandler(
        val status: Int,
        override val message: String = ""
    ) : ResponseStatusException(HttpStatusCode.valueOf(status), message)

    data class NotFoundException(
        val errorDetails: ErrorDetails
    ) : SetExceptionHandler(404, errorDetails.message)

    data class BadRequestException(
        val errorDetails: String
    ) : SetExceptionHandler(400, errorDetails)

    data class UnauthorizedException(
        val errorDetails: String
    ) : SetExceptionHandler(401, errorDetails)

    data class UnValidTokenException(
        val errorDetails: String
    ) : SetExceptionHandler(401, errorDetails)

    data class ExpiredTokenException(
        val errorDetails: String
    ) : SetExceptionHandler(401, errorDetails)

    data class NotSupportTokenException(
        val errorDetails: String
    ) : SetExceptionHandler(403, errorDetails)

    data class AccessDeniedException(
        val errorDetails: String
    ) : SetExceptionHandler(403, errorDetails)

    data class FailToVerityTokenException(
        val errorDetails: String
    ) : SetExceptionHandler(401, errorDetails)

    data class AlreadyExistsException(
        val errorDetails: String
    ) : SetExceptionHandler(409, errorDetails)

    data class FileUploadException(
        val errorDetails: ErrorDetails
    ) : SetExceptionHandler(413, errorDetails.message)
}
