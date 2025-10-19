package com.github.vitorhenriquec.accountcore.infrastructure.repositories

import com.github.vitorhenriquec.accountcore.infrastructure.entities.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository: JpaRepository<Account, Long> {

    fun findByDocumentNumber(documentNumber: String): Optional<Account>
}