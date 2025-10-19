package com.github.vitorhenriquec.accountcore.unit.infrastructure.resource

import com.github.vitorhenriquec.accountcore.infrastructure.request.SaveAccountRequest
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountAlreadyExistException
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.domain.usecase.FindAccountUseCaseImpl
import com.github.vitorhenriquec.accountcore.domain.usecase.SaveAccountUseCaseImpl
import com.github.vitorhenriquec.accountcore.infrastructure.resouce.AccountResource
import com.github.vitorhenriquec.accountcore.infrastructure.response.FindAccountResponse
import com.github.vitorhenriquec.accountcore.infrastructure.response.SaveAccountResponse
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyLong
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertEquals

@WebMvcTest(AccountResource::class)
class AccountResourceTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var usecase: SaveAccountUseCaseImpl

    @MockkBean
    private lateinit var findAccountUseCase: FindAccountUseCaseImpl

    private val objectMapper = ObjectMapper()

    @Test
    fun `Should successfully save an account`() {
        val documentNumber = "12345678910"

        val accountRequest = objectMapper.writeValueAsString(
            SaveAccountRequest(
                documentNumber = documentNumber
            )
        )

        every {
            usecase.save(any())
        } returns (1L)

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
        val documentNumber = "12345678911"

        every{
            usecase.save(any())
        } throws AccountAlreadyExistException()


        val accountRequest = objectMapper.writeValueAsString(
            SaveAccountRequest(
                documentNumber = documentNumber
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
    fun `Should find account`() {
        val documentNumber = "12345678911"

        val accountModel = AccountModel(
            id = 12L,
            documentNumber = documentNumber
        )

        every{
            findAccountUseCase.findById(anyLong())
        } returns (accountModel)

        val result = mockMvc.perform(
            MockMvcRequestBuilders.get("/accounts/12")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()

        val responseBody = result.response.contentAsString
        val accountResponse = objectMapper.readValue(responseBody, FindAccountResponse::class.java)

        assertEquals(accountResponse.id, 12L)
        assertEquals(accountResponse.documentNumber, documentNumber)
    }

    @Test
    fun `Should not find an account when account is not saved`() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts/100")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isNotFound())
    }

    @Test
    fun `Should not find an account when account id is not sent`() {

        mockMvc.perform(
            MockMvcRequestBuilders.post("/accounts/")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(MockMvcResultHandlers.print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    }
}