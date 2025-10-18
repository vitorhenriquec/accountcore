package com.github.vitorhenriquec.accountcore.infrastructure.adapters

import com.github.vitorhenriquec.accountcore.domain.gateway.AccountDatabase
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import org.springframework.stereotype.Component

@Component
class AccountDatabaseAdapter(
    private val repo: AccountRepository
): AccountDatabase {

    override fun save(account: AccountModel): AccountModel {
        TODO("Not yet implemented")
    }

}