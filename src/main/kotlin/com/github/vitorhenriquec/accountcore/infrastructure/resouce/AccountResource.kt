package com.github.vitorhenriquec.accountcore.infrastructure.resouce

import com.github.vitorhenriquec.accountcore.infrastructure.request.AccountRequest
import com.github.vitorhenriquec.accountcore.infrastructure.response.AccountResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus

@Controller
@RequestMapping("accounts")
class AccountResource {

    //@ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    fun saveAccount(
        //@RequestBody //@Validated
        request: AccountRequest
    ): AccountResponse {
        TODO()
    }
}