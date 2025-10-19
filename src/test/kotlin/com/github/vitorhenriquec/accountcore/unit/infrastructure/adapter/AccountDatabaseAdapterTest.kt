package com.github.vitorhenriquec.accountcore.unit.infrastructure.adapter

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException


class AccountDatabaseAdapterTest {
    private val repo = mock<AccountRepository>()
    private val adapter = AccountDatabaseAdapter(repo)

    @Test
    fun `Should save an account`() {
        val account = AccountModel(
            id = 1241212L,
            documentNumber = "12332099800"
        )

        `when`(
            repo.save(any())
        ).thenReturn(account.toEntity())

        val accountResult = assertDoesNotThrow {
            adapter.save(account)
        }
        assertEquals(account.id, accountResult.id)
        assertEquals(account.documentNumber, "12332099800")
    }

    @Test
    fun `Should not save an account`() {
        val account = AccountModel()

        `when`(
            repo.save(any())
        ).thenThrow(DataIntegrityViolationException("Duplicate key"))

        assertThrows<DataIntegrityViolationException> {
            adapter.save(account)
        }

    }
}