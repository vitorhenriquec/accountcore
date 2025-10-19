package com.github.vitorhenriquec.accountcore.infrastructure.adapters

import com.github.vitorhenriquec.accountcore.domain.gateway.AccountDatabase
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountAlreadyExistException
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import com.github.vitorhenriquec.accountcore.infrastructure.util.toModel
import org.springframework.stereotype.Component

@Component
class AccountDatabaseAdapter(
    private val repo: AccountRepository
): AccountDatabase {

    override fun save(account: AccountModel): AccountModel {

        val entity = account.toEntity()

        val optionalAccount = repo.findByDocumentNumber(account.documentNumber)

        if(optionalAccount.isPresent) {
            throw AccountAlreadyExistException()
        }

        return repo.save(entity).toModel()
    }

    override fun findById(id: Long): AccountModel {
        TODO("Not yet implemented")
    }

}


