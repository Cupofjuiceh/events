package com.test.events.service.user.impl

import com.test.events.dto.user.ActivateEmailDTO
import com.test.events.dto.user.NewUserDTO
import com.test.events.dto.user.UpdateUserDTO
import com.test.events.exception.GenericException
import com.test.events.model.user.Roles
import com.test.events.model.user.User
import com.test.events.repository.datajpa.UserRepository
import com.test.events.service.processing.KafkaMessageProducer
import com.test.events.service.user.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random

@Service
class UserServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val kafkaMessageProducer: KafkaMessageProducer
) : UserService {

    override fun activateUser(activateEmailDTO: ActivateEmailDTO): Boolean {
        val user = userRepository.findByEmail(activateEmailDTO.email).orElseThrow{ throw GenericException(HttpStatus.NOT_FOUND, "User not found.") }

        if (user.activationCode == activateEmailDTO.code) {
            user.isEmailVerified = true
            return true
        } else {
            throw GenericException(HttpStatus.BAD_REQUEST, "Wrong activation code.")
        }
    }

    override fun createUser(newUserDTO: NewUserDTO): User {
        if (userRepository.findByEmail(newUserDTO.email).isPresent) {
            throw GenericException(HttpStatus.BAD_REQUEST, "Email already in use.")
        }

        var newUser = convertNewUserDTO(newUserDTO)
        newUser.activationCode = Random.nextInt(1000, 9999)
        newUser.dateJoined = LocalDateTime.now()
        newUser.roles.add(Roles.REGISTERED_USER)

        newUser = userRepository.save(newUser)

        kafkaMessageProducer.sendEmailVerificationCode(newUser, newUser.activationCode)

        return newUser
    }

    override fun findById(id: UUID): User =
            userRepository
                    .findById(id)
                    .orElseThrow {
                        throw GenericException(HttpStatus.NOT_FOUND, "User not found")
                    }

    override fun findByEmail(email: String): User =
            userRepository
                    .findByEmail(email)
                    .orElseThrow {
                        throw GenericException(HttpStatus.NOT_FOUND, "User not found")
                    }

    override fun updateUser(userId: UUID, updateUserDTO: UpdateUserDTO): User {
        val user = findById(userId)
        var userChanged = false

        if (!updateUserDTO.email.isNullOrBlank() && updateUserDTO.email != user.email) {
            user.email = updateUserDTO.email
            userChanged = true
        }

        if (!updateUserDTO.fullName.isNullOrBlank() && updateUserDTO.fullName != user.fullName) {
            user.fullName = updateUserDTO.fullName
            userChanged = true
        }

        return if (userChanged) {
            userRepository.save(user)
        } else {
            user
        }
    }

    override fun loadUserByUsername(userName: String): UserDetails =
            userRepository
                    .findByEmail(userName)
                    .orElseThrow {
                        throw GenericException(HttpStatus.NOT_FOUND, "User not found")
                    }

    private fun convertNewUserDTO(newUserDTO: NewUserDTO): User =
            User(
                    email = newUserDTO.email,
                    password = passwordEncoder.encode(newUserDTO.password),
                    fullName = newUserDTO.fullName
            )
}