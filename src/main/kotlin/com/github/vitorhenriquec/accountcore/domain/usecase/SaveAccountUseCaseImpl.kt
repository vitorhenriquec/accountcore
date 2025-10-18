package com.github.vitorhenriquec.accountcore.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.gateway.AccountDatabase

class SaveAccountUseCaseImpl(
    private val adapter: AccountDatabase
): SaveAccountUseCase {

    override fun save(documentNumber: String): Long {
        TODO("Not yet implemented")
    }

}
