package com.github.vitorhenriquec.accountcore.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import org.springframework.stereotype.Service

@Service
class FindAccountUseCaseImpl(
    private val adapter: AccountDatabaseAdapter
) : FindAccountUseCase {

    override fun findById(id: Long): AccountModel {
        return adapter.findById(id)
    }
}