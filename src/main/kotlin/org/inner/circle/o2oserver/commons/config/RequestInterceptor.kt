package org.inner.circle.o2oserver.commons.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.inner.circle.o2oserver.commons.security.TokenProvider
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class RequestInterceptor(
    private val objectMapper: ObjectMapper,
    private val tokenProvider: TokenProvider
) : HandlerInterceptor {
    private val log = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        log.info("Method: ${request.method}, URI : ${request.requestURL}")
        return true
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        log.info("Method: ${request.method}, URI : ${request.requestURL}, Response Status : ${response.status}")
    }
}
