package com.github.vitorhenriquec.accountcore.infrastructure.util

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.entities.Account

fun AccountModel.toEntity() = Account(
    id = this.id,
    documentNumber = this.documentNumber,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)

fun Account.toModel() = AccountModel(
    id = this.id,
    documentNumber = this.documentNumber,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)
