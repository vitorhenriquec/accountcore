package com.github.vitorhenriquec.accountcore.infrastructure.request

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.NotBlank

@JsonNaming(value= PropertyNamingStrategies.SnakeCaseStrategy::class)
data class SaveAccountRequest(
    @field:NotBlank(message = "Document number is required")
    val documentNumber: String
)