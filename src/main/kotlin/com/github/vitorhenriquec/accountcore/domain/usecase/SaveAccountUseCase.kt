package com.github.vitorhenriquec.accountcore.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel

interface SaveAccountUseCase {
    fun save(accountToSave: AccountModel): Long
}
