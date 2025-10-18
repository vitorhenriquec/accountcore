package com.github.vitorhenriquec.accountcore.infrastructure.repositories

import com.github.vitorhenriquec.accountcore.infrastructure.entities.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository: JpaRepository<Account, Long> {
}