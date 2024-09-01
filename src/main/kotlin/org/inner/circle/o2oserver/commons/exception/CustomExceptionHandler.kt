package org.inner.circle.o2oserver.commons.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import org.apache.catalina.connector.ClientAbortException
import org.inner.circle.o2oserver.commons.models.BaseResponse
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.HttpMediaTypeNotAcceptableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.async.AsyncRequestTimeoutException
import org.springframework.web.multipart.MaxUploadSizeExceededException

@RestControllerAdvice
class CustomExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(Exceptions.NotFoundException::class)
    fun handleException(e: Exceptions.NotFoundException): ResponseEntity<BaseResponse> {
        log.error("NotFound error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.BadRequestException::class)
    fun handleException(e: Exceptions.BadRequestException): ResponseEntity<BaseResponse> {
        log.error("BadRequest error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.UnauthorizedException::class)
    fun handleException(e: Exceptions.UnauthorizedException): ResponseEntity<BaseResponse> {
        log.error("Unauthorized error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.UnValidTokenException::class)
    fun handleException(e: Exceptions.UnValidTokenException): ResponseEntity<BaseResponse> {
        log.error("UnValidToken error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.ExpiredTokenException::class)
    fun handleException(e: Exceptions.ExpiredTokenException): ResponseEntity<BaseResponse> {
        log.error("ExpiredToken error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.NotSupportTokenException::class)
    fun handleException(e: Exceptions.NotSupportTokenException): ResponseEntity<BaseResponse> {
        log.error("NotSupportToken error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.AccessDeniedException::class)
    fun handleException(e: Exceptions.AccessDeniedException): ResponseEntity<BaseResponse> {
        log.error("AccessDenied error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.FailToVerityTokenException::class)
    fun handleException(e: Exceptions.FailToVerityTokenException): ResponseEntity<BaseResponse> {
        log.error("FailToVerityToken error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.AlreadyExistsException::class)
    fun handleException(e: Exceptions.AlreadyExistsException): ResponseEntity<BaseResponse> {
        log.error("AlreadyExists error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exceptions.FileUploadException::class)
    fun handleException(e: Exceptions.FileUploadException): ResponseEntity<BaseResponse> {
        log.error("File Upload error response !!! : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(e.status).body(
            BaseResponse(
                response = e.message,
                statusCode = e.status,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleException(e: AuthenticationException): ResponseEntity<BaseResponse> {
        val message = "인증에 실패했습니다. 다시 로그인해 주세요"
        log.error("error response : $message")
        log.error("excepion : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(401).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 401,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(MalformedJwtException::class)
    fun handleException(e: MalformedJwtException): ResponseEntity<BaseResponse> {
        val message = "유효하지 않은 토큰입니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(401).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 401,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(ExpiredJwtException::class)
    fun handleException(e: ExpiredJwtException): ResponseEntity<BaseResponse> {
        val message = "이미 만료된 토큰입니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(401).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 401,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(CustomAuthenticationException::class)
    fun handleException(e: CustomAuthenticationException): ResponseEntity<BaseResponse> {
        log.error("error response : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(401).body(
            BaseResponse(
                response = e.message ?: "인증에 실패했습니다. 다시 로그인해 주세요",
                statusCode = 401,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<BaseResponse> {
        val message = "알 수 없는 오류가 발생했습니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(500).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 500,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    fun handleException(e: HttpMediaTypeNotSupportedException): ResponseEntity<BaseResponse> {
        val message = "지원하지 않는 미디어 타입입니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(415).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 415,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(e: IllegalArgumentException): ResponseEntity<BaseResponse> {
        val message = "잘못된 인자가 전달되었습니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(UnsupportedOperationException::class)
    fun handleException(e: UnsupportedOperationException): ResponseEntity<BaseResponse> {
        val message = "지원하지 않는 연산입니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleException(e: MaxUploadSizeExceededException): ResponseEntity<BaseResponse> {
        val message = "파일 업로드 크기가 초과되었습니다."
        log.error("error response : $message")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: message,
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException::class)
    fun handleException(e: HttpMediaTypeNotAcceptableException): ResponseEntity<BaseResponse> {
        log.error("error response : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: "지원하지 않는 미디어 타입입니다.",
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(ClientAbortException::class)
    fun handleException(e: ClientAbortException): ResponseEntity<BaseResponse> {
        log.error("error response : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: "클라이언트 연결이 끊겼습니다.",
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(AsyncRequestTimeoutException::class)
    fun handleException(e: AsyncRequestTimeoutException): ResponseEntity<BaseResponse> {
        log.error("error response : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: "요청 시간이 초과되었습니다.",
                statusCode = 400,
                msg = "fail",
            ),
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(e: MethodArgumentNotValidException): ResponseEntity<BaseResponse> {
        log.error("error response : ${e.message}")
        e.printStackTrace()
        return ResponseEntity.status(400).body(
            BaseResponse(
                response = e.message ?: "데이터가 유효하지 않습니다.",
                statusCode = 400,
                msg = "fail",
            ),
        )
    }
}
