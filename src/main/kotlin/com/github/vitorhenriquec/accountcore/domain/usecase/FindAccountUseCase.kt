package com.github.vitorhenriquec.accountcore.domain.usecase

interface FindAccountUseCase {
    fun findById(id: Long): Pair<Long, String>
}