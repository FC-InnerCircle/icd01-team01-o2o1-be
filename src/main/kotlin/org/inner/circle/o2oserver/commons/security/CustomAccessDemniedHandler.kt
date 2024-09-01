package org.inner.circle.o2oserver.commons.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDemniedHandler : AccessDeniedHandler {
    override fun handle(request: HttpServletRequest?, response: HttpServletResponse?, exception: AccessDeniedException?) {
        response?.sendError(HttpServletResponse.SC_FORBIDDEN, exception?.message ?: "Access Denied")
    }
}
