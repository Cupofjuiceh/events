package com.test.events.service.user.jwtutil

import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthEntryPoint : AuthenticationEntryPoint {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(AuthEntryPoint::class.java)
    }

    @Throws(IOException::class, ServletException::class)
    override fun commence(request: HttpServletRequest,
                          response: HttpServletResponse,
                          ex: AuthenticationException) {

        LOGGER.error("Authorization error: ${ex.message}")
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid credentials")
    }
}