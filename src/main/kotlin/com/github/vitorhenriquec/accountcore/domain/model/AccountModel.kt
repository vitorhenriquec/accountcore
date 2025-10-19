package com.github.vitorhenriquec.accountcore.domain.model

import com.github.vitorhenriquec.accountcore.infrastructure.util.nowInSaoPaulo
import java.time.LocalDateTime

data class AccountModel(
    val id: Long = 0L,
    val documentNumber: String = "",
    val createdAt: LocalDateTime = LocalDateTime.now().nowInSaoPaulo(),
    val updatedAt: LocalDateTime? = null
)