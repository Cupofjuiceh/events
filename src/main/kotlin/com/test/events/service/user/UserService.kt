package com.test.events.service.user

import com.test.events.dto.user.ActivateEmailDTO
import com.test.events.dto.user.LoginDTO
import com.test.events.dto.user.NewUserDTO
import com.test.events.dto.user.UpdateUserDTO
import com.test.events.model.user.JwtResponse
import com.test.events.model.user.User
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.*

interface UserService: UserDetailsService {
    fun createUser(newUserDTO: NewUserDTO): User
    fun findById(id: UUID): User
    fun updateUser(userId: UUID, updateUserDTO: UpdateUserDTO): User
    fun activateUser(activateEmailDTO: ActivateEmailDTO): Boolean
    fun findByEmail(email: String): User
}