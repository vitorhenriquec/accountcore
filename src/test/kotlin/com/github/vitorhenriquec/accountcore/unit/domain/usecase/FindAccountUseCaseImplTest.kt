package com.github.vitorhenriquec.accountcore.unit.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountNotFoundException
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.domain.usecase.FindAccountUseCaseImpl
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FindAccountUseCaseImplTest {

    private val adapter = mock<AccountDatabaseAdapter>()

    private val usecase = FindAccountUseCaseImpl(
        adapter
    )

    @Test
    fun `Should find an account`() {
        val documentNumber = "313131313131313"
        val account = AccountModel(id = 12L, documentNumber = documentNumber)

        `when`(
            adapter.findById(account.id)
        ).thenReturn(account)

        val pairResult = assertDoesNotThrow {
            usecase.findById(account.id)
        }

        assertEquals(Pair(account.id, account.documentNumber), pairResult)
    }

    @Test
    fun `Should not find an account`() {
        val documentNumber = "313131313131313"
        val account = AccountModel(id = 12L, documentNumber = documentNumber)

        `when`(
            adapter.findById(account.id)
        ).thenReturn(account)

        assertThrows<AccountNotFoundException> {
            usecase.findById(account.id)
        }
    }
}