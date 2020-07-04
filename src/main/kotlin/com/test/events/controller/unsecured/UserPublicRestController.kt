package com.test.events.controller.unsecured

import com.test.events.dto.user.ActivateEmailDTO
import com.test.events.dto.user.NewUserDTO
import com.test.events.service.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserPublicRestController(
        private val userService: UserService
) {
    @PostMapping("/register")
    fun registerWithEmail(newUserDTO: NewUserDTO) =
            try {
                ResponseEntity.ok(userService.saveNewUser(newUserDTO))
            } catch (ex: Exception) {}

    @PostMapping("/activate")
    fun activateEmail(activateEmailDTO: ActivateEmailDTO) =
            try {
                ResponseEntity.ok(userService.activateUser(activateEmailDTO))
            } catch (ex: Exception) {}
}
