package com.github.vitorhenriquec.accountcore.infrastructure.resouce

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.domain.usecase.SaveAccountUseCase
import com.github.vitorhenriquec.accountcore.infrastructure.request.SaveAccountRequest
import com.github.vitorhenriquec.accountcore.infrastructure.response.SaveAccountResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("accounts")
class AccountResource(
    @Autowired
    private val saveAccountUseCase: SaveAccountUseCase
) {

    @PostMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveAccount(
        @RequestBody @Validated
        request: SaveAccountRequest
    ): ResponseEntity<SaveAccountResponse> {
        return ResponseEntity.status(201).body(
            SaveAccountResponse(
                id = saveAccountUseCase.save(
                    AccountModel(
                        documentNumber = request.documentNumber
                    )
                ),
                documentNumber = request.documentNumber
            )
        )
    }
}