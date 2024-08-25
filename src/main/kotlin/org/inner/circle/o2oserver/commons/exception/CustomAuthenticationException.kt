package org.inner.circle.o2oserver.commons.exception

import org.springframework.security.core.AuthenticationException

class CustomAuthenticationException(msg: String?) : AuthenticationException(msg)
