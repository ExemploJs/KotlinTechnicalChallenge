package com.technical.challenge.exception.handler

import com.technical.challenge.*
import com.technical.challenge.exception.response.HandlerResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*


@ControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(value = [ UserNotFoundException::class, AccountNotFoundException::class])
    fun handleNotFound(e: RuntimeException, request: WebRequest?): ResponseEntity<*> {
        return ResponseEntity<Any>(
            HandlerResponse(
                HttpStatus.NOT_FOUND.value(),
                Date(), e.message!!
            ), HttpStatus.NOT_FOUND
        )
    }

    @ExceptionHandler(value = [AccountDoesntHaveBalanceException::class, FromUserIdCannotBeTheSameOfToUserIdException::class, FieldCannotBeNullException::class, ValueCannotBeNegativeOrZeroException::class])
    fun handleBusinessRuleError(
        e: RuntimeException, request: WebRequest?
    ): ResponseEntity<*>? {
        return ResponseEntity(
            HandlerResponse(
                HttpStatus.BAD_REQUEST.value(),
                Date(), e.message!!
            ), HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobally(
        e: Exception, request: WebRequest?
    ): ResponseEntity<*>? {
        val errorDetail = HandlerResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Date(),
            e.message!!
        )
        return ResponseEntity(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}