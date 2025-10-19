package com.github.vitorhenriquec.accountcore.domain.gateway

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel

interface AccountDatabase {
    fun save(account: AccountModel): AccountModel
}