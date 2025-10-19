package com.github.vitorhenriquec.accountcore.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel

interface FindAccountUseCase {
    fun findById(id: Long): AccountModel
}