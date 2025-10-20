package com.github.vitorhenriquec.accountcore.infrastructure.handler

import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountAlreadyExistException
import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class AccountHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(exception = [AccountAlreadyExistException::class])
    fun handleAccountAlreadyExistException(
        exception: AccountAlreadyExistException
    ): ResponseEntity<Any?>?{
        return ResponseEntity.status(HttpStatus.CONFLICT).build()
    }

    @ExceptionHandler(exception = [AccountNotFoundException::class])
    fun handleAccountAlreadyExistException(
        exception: AccountNotFoundException
    ): ResponseEntity<Any?>?{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }
}