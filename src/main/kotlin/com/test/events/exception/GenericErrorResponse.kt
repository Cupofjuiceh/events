package com.test.events.exception

data class GenericErrorResponse(
        val code: Int,
        val description: String
) {
    constructor(ex: Exception) : this(500, ex.message.toString())
    constructor(ex: Exception, code: Int) : this(code, ex.message.toString())
    constructor(ex: GenericException) : this(ex.httpStatus.value(), ex.description)
}