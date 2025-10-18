package com.github.vitorhenriquec.accountcore.domain.model

import com.github.vitorhenriquec.accountcore.infrastructure.util.nowInSaoPaulo
import java.time.LocalDateTime

class AccountModel() {
    var id: Long = 0L
    var documentNumber: String = ""
    var createdAt: LocalDateTime = LocalDateTime.now().nowInSaoPaulo()
    var updatedAt: LocalDateTime? = null

    constructor(
        id: Long,
        documentNumber: String,
        createdAt: LocalDateTime,
        updatedAt: LocalDateTime?
    ) : this() {
        this.documentNumber = documentNumber
        this.id = id
        this.createdAt = createdAt
        this.updatedAt = updatedAt
    }
}