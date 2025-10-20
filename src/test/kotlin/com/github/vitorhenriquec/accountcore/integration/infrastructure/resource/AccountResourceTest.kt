package com.github.vitorhenriquec.accountcore.integration.infrastructure.resource

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.request.SaveAccountRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import com.github.vitorhenriquec.accountcore.infrastructure.response.FindAccountResponse
import com.github.vitorhenriquec.accountcore.infrastructure.response.SaveAccountResponse
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = ["test"])
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
            SaveAccountRequest(
                documentNumber = documentNumber
            )
        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountRequest)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn()

        val responseBody = result.response.contentAsString
        val accountResponse = objectMapper.readValue(responseBody, SaveAccountResponse::class.java)

        assertEquals(accountResponse.documentNumber, documentNumber)
    }

    @Test
    fun `Should not save an account when document is empty`() {
        val accountRequest = objectMapper.writeValueAsString(
            SaveAccountRequest(
                documentNumber = ""
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountRequest)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }

    @Test
    fun `Should not save an account is invalid`() {
        accountRepository.saveAndFlush(
            AccountModel(
                documentNumber = "12345678911"
            ).toEntity()
        )

        val accountRequest = objectMapper.writeValueAsString(
            SaveAccountRequest(
                documentNumber = "12345678911"
            )
        )

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(accountRequest)
                .accept(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isConflict())
    }

    @Test
    fun `Should find an account`() {
        val documentNumber = "12345678912"

        val accountSaved = accountRepository.saveAndFlush(
            AccountModel(
                documentNumber = documentNumber
            ).toEntity()
        )

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/${accountSaved.id}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()

        val responseBody = result.response.contentAsString
        val accountResponse = objectMapper.readValue(responseBody, FindAccountResponse::class.java)

        assertEquals(accountResponse.id, accountSaved.id)
        assertEquals(accountResponse.documentNumber, documentNumber)
    }

    @Test
    fun `Should not find an account`() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/25")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    fun `Should not find an account when id not sent`() {

        mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/{}")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }
}