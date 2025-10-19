package com.github.vitorhenriquec.accountcore.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.gateway.AccountDatabase
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import org.springframework.stereotype.Service

@Service
class SaveAccountUseCaseImpl(
    private val adapter: AccountDatabase
): SaveAccountUseCase {

    override fun save(accountToSave: AccountModel): Long =
        adapter.save(accountToSave).id

}
