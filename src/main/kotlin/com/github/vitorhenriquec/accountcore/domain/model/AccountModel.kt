package com.github.vitorhenriquec.accountcore.domain.model

import com.github.vitorhenriquec.accountcore.domain.util.nowInSaoPaulo
import java.time.LocalDateTime

class AccountModel {
    var id: Long = 0L
    var documentNumber: String = ""
    var createdAt: LocalDateTime = LocalDateTime.now().nowInSaoPaulo()
    var updatedAt: LocalDateTime? = null
}