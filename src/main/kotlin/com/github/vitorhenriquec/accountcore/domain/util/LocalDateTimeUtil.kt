package com.github.vitorhenriquec.accountcore.domain.util

import java.time.LocalDateTime
import java.time.ZoneId

fun LocalDateTime.nowInSaoPaulo(): LocalDateTime {
    return LocalDateTime.now(ZoneId.of("America/Sao_Paulo"))
}