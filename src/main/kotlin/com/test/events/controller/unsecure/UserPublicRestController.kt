package com.test.events.controller.unsecure

import com.test.events.dto.user.ActivateEmailDTO
import com.test.events.dto.user.LoginDTO
import com.test.events.dto.user.NewUserDTO
import com.test.events.dto.user.UserResponseDTO
import com.test.events.service.user.AuthenticationService
import com.test.events.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserPublicRestController(
        private val userService: UserService,
        private val authenticationService: AuthenticationService
) {
    @PostMapping("/register")
    fun registerWithEmail(@RequestBody newUserDTO: NewUserDTO) =
            ResponseEntity.ok(UserResponseDTO(userService.createUser(newUserDTO)))

    @PostMapping("/login")
    fun login(@RequestBody loginDTO: LoginDTO) =
            ResponseEntity.ok(authenticationService.authorize(loginDTO))

    @PostMapping("/activate")
    fun activateEmail(@RequestBody activateEmailDTO: ActivateEmailDTO) =
            ResponseEntity.ok(userService.activateUser(activateEmailDTO))
}
