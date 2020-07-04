package com.test.events.dto.user

data class ActivateEmailDTO(
        val email: String,
        val code: Int
)