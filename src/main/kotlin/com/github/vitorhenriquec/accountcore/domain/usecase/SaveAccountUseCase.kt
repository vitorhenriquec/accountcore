package com.github.vitorhenriquec.accountcore.domain.usecase

interface SaveAccountUseCase {
    fun save(documentNumber: String): Long
}
