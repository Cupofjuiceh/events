package com.test.events.dto.user

import com.test.events.model.event.Event
import com.test.events.model.user.Roles
import com.test.events.model.user.User
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

data class UserResponseDTO(
        var id: UUID? = null,
        var email: String = "",
        var fullName: String = "",
        var isEmailVerified: Boolean = false,
        var dateJoined: LocalDateTime? = null,
        val roles: MutableSet<Roles> = mutableSetOf()
) {
    constructor(user: User) : this(
            id = user.id,
            email = user.email,
            fullName = user.fullName,
            isEmailVerified = user.isEmailVerified,
            dateJoined = user.dateJoined,
            roles = user.roles
    )
}