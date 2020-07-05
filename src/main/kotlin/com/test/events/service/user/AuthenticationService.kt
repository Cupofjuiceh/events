package com.test.events.service.user

import com.test.events.dto.user.LoginDTO
import org.springframework.http.ResponseEntity

interface AuthenticationService {
    fun authorize(loginDTO: LoginDTO): ResponseEntity<*>
}