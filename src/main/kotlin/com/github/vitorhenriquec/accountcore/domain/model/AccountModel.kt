package com.github.vitorhenriquec.accountcore.domain.model

import java.time.LocalDateTime

class AccountModel {
    var id: Long = 0L
    var documentNumber: String = ""
    var createdAt: LocalDateTime = LocalDateTime.now()
    var updatedAt: LocalDateTime? = null
}