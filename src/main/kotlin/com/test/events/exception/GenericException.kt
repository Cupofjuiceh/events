package com.test.events.exception

import org.springframework.http.HttpStatus

data class GenericException(
        val httpStatus: HttpStatus,
        val description: String
) : RuntimeException (description)