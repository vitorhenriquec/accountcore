package com.github.vitorhenriquec.accountcore.infrastructure.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SaveAccountResponse(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("document_number")
    val documentNumber: String
)