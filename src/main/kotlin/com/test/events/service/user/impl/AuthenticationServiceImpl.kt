package com.test.events.service.user.impl

import com.test.events.dto.user.LoginDTO
import com.test.events.model.user.JwtResponse
import com.test.events.service.user.AuthenticationService
import com.test.events.service.user.jwtutil.TokenProvider
import com.test.events.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class AuthenticationServiceImpl(
        private val userService: UserService,
        private val authenticationManager: AuthenticationManager,
        private val tokenProvider: TokenProvider
): AuthenticationService {
    override fun authorize(loginDTO: LoginDTO): ResponseEntity<*> {

        val user = userService.findByEmail(loginDTO.email)
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginDTO.email, loginDTO.password))

        SecurityContextHolder.getContext().authentication = authentication

        val token: String = tokenProvider.generateToken(user.username)
        val authorities: List<GrantedAuthority> = user.roles.stream().map { role -> SimpleGrantedAuthority(role.name) }.collect(Collectors.toList<GrantedAuthority>())
        return ResponseEntity(JwtResponse(token, user.username, authorities), HttpStatus.OK)
    }
}