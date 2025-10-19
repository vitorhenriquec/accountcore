package com.github.vitorhenriquec.accountcore.integration.infrastructure.resource

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import com.github.vitorhenriquec.accountcore.infrastructure.request.AccountRequest
import com.github.vitorhenriquec.accountcore.infrastructure.resouce.AccountResource
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import com.github.vitorhenriquec.accountcore.infrastructure.response.AccountResponse
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

//@WebMvcTest(AccountResource::class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = ["test"])
@Transactional
class AccountResourceTest(
    @Autowired
    private val mockMvc: MockMvc,
    @Autowired
    private val accountRepository: AccountRepository,
) {

    private val objectMapper = ObjectMapper()

    @Test
    fun `Should successfully save an account`() {
        val documentNumber = "12345678910"

        val accountRequest = objectMapper.writeValueAsString(
            AccountRequest(
                documentNumber = documentNumber
            )
        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountRequest)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andReturn()

        val responseBody = result.response.contentAsString
        val accountResponse = objectMapper.readValue(responseBody, AccountResponse::class.java)

        assertEquals(accountResponse.documentNumber, documentNumber)
    }

    @Test
    fun `Should not save an account when document is empty`() {
        val accountRequest = objectMapper.writeValueAsString(
            AccountRequest(
                documentNumber = ""
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }

    @Test
    fun `Should not save an account is invalid`() {
        accountRepository.save(
            AccountModel(
                documentNumber = "12345678911"
            ).toEntity()
        )

        val accountRequest = objectMapper.writeValueAsString(
            AccountRequest(
                documentNumber = "12345678911"
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isConflict())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }
}