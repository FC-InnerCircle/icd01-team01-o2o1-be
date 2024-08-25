package org.inner.circle.o2oserver.commons.exception

import io.github.oshai.kotlinlogging.KotlinLogging
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.apache.catalina.connector.ClientAbortException
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class CustomExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ExceptionHandler(Exceptions.NotFoundException::class)
    fun handleException(e: Exceptions.NotFoundException): ResponseEntity<ExceptionResponse> {
        log.error { "NotFound error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.BadRequestException::class)
    fun handleException(e: Exceptions.BadRequestException): ResponseEntity<ExceptionResponse> {
        log.error { "BadRequest error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.UnauthorizedException::class)
    fun handleException(e: Exceptions.UnauthorizedException): ResponseEntity<ExceptionResponse> {
        log.error { "Unauthorized error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.UnValidTokenException::class)
    fun handleException(e: Exceptions.UnValidTokenException): ResponseEntity<ExceptionResponse> {
        log.error { "UnValidToken error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.ExpiredTokenException::class)
    fun handleException(e: Exceptions.ExpiredTokenException): ResponseEntity<ExceptionResponse> {
        log.error { "ExpiredToken error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.NotSupportTokenException::class)
    fun handleException(e: Exceptions.NotSupportTokenException): ResponseEntity<ExceptionResponse> {
        log.error { "NotSupportToken error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.AccessDeniedException::class)
    fun handleException(e: Exceptions.AccessDeniedException): ResponseEntity<ExceptionResponse> {
        log.error { "AccessDenied error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.FailToVerityTokenException::class)
    fun handleException(e: Exceptions.FailToVerityTokenException): ResponseEntity<ExceptionResponse> {
        log.error { "FailToVerityToken error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.AlreadyExistsException::class)
    fun handleException(e: Exceptions.AlreadyExistsException): ResponseEntity<ExceptionResponse> {
        log.error { "AlreadyExists error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(Exceptions.FileUploadException::class)
    fun handleException(e: Exceptions.FileUploadException): ResponseEntity<ExceptionResponse> {
        log.error { "File Upload error response !!! : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(ExceptionResponse(e.message))
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleException(e: AuthenticationException): ResponseEntity<ExceptionResponse> {
        val message = "인증에 실패했습니다. 다시 로그인해 주세요"
        log.error { "error response : $message" }
        log.error { "excepion : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(401).body(ExceptionResponse(message))
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleException(e: MalformedJwtException): ResponseEntity<ExceptionResponse> {
        val message = "유효하지 않은 토큰입니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(401).body(ExceptionResponse(message))
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleException(e: ExpiredJwtException): ResponseEntity<ExceptionResponse> {
        val message = "이미 만료된 토큰입니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(401).body(ExceptionResponse(message))
    }

    @ExceptionHandler(CustomAuthenticationException::class)
    fun handleException(e: CustomAuthenticationException): ResponseEntity<ExceptionResponse> {
        log.error { "error response : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(401).body(ExceptionResponse(e.message!!))
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ExceptionResponse> {
        val message = "알 수 없는 오류가 발생했습니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(500).body(ExceptionResponse(e.message ?: message))
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleException(e: HttpMediaTypeNotSupportedException): ResponseEntity<ExceptionResponse> {
        val message = "지원하지 않는 미디어 타입입니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(415).body(ExceptionResponse(message))
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(e: IllegalArgumentException): ResponseEntity<ExceptionResponse> {
        val message = "잘못된 인자가 전달되었습니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(message))
    }

    @ExceptionHandler(UnsupportedOperationException::class)
    fun handleException(e: UnsupportedOperationException): ResponseEntity<ExceptionResponse> {
        val message = "지원하지 않는 연산입니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(message))
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleException(e: MaxUploadSizeExceededException): ResponseEntity<ExceptionResponse> {
        val message = "파일 업로드 크기가 초과되었습니다."
        log.error { "error response : $message" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(message))
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleException(e: HttpMediaTypeNotAcceptableException): ResponseEntity<ExceptionResponse> {
        log.error { "error response : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(e.message!!))
    }

    @ExceptionHandler(ClientAbortException::class)
    fun handleException(e: ClientAbortException): ResponseEntity<ExceptionResponse> {
        log.error { "error response : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(e.message!!))
    }

    @ExceptionHandler(AsyncRequestTimeoutException::class)
    fun handleException(e: AsyncRequestTimeoutException): ResponseEntity<ExceptionResponse> {
        log.error { "error response : ${e.message}" }
        e.printStackTrace()
        return ResponseEntity.status(400).body(ExceptionResponse(e.message!!))
    }
}
