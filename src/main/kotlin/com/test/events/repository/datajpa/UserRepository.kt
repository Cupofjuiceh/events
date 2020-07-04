package com.test.events.repository.datajpa

import com.test.events.model.user.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository: CrudRepository<User, UUID> {
    fun findByEmail(email: String): Optional<User>
}