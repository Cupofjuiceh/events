package com.test.events.service.user.util

import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder

object UserUtil {
    fun isAuthorised(): Boolean =
            (SecurityContextHolder.getContext().authentication.isAuthenticated
                    && SecurityContextHolder.getContext().authentication !is AnonymousAuthenticationToken)

}