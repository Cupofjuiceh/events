package com.test.events.exception

import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionAdvice {
    val logger = LoggerFactory.getLogger(ExceptionAdvice::class.java)

    @ExceptionHandler(GenericException::class)
    fun handleGenericException(ex: GenericException): ResponseEntity<Any> {
        logger.error("Handling GenericException with error status: ${ex.httpStatus} and description: ${ex.description}", ex)
        val errorResponse: Map<String, List<GenericErrorResponse>> = mapOf(Pair("errors", listOf(GenericErrorResponse(ex.httpStatus.value(), ex.description))))
        return ResponseEntity(errorResponse, ex.httpStatus)
    }

    @ExceptionHandler(Exception::class)
    fun handleExceptions(ex: Exception): ResponseEntity<Any> {
        logger.error("Handling Exception with a message: ${ex.message}", ex)

        val errorResponse: Map<String, List<GenericErrorResponse>> = mapOf(Pair("errors", listOf(GenericErrorResponse(500, ex.message.toString()))))
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}