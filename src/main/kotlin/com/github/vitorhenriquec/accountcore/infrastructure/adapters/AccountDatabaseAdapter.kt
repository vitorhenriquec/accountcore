package com.github.vitorhenriquec.accountcore.infrastructure.adapters

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository

class AccountDatabaseAdapter(
    private val repo: AccountRepository
) {

    fun save(): AccountModel {
        TODO("Not yet implemented")
    }

}