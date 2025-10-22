package com.github.vitorhenriquec.accountcore.unit.infrastructure.adapter

import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountAlreadyExistException
import com.github.vitorhenriquec.accountcore.domain.exceptions.AccountNotFoundException
import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException
import java.util.Optional


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

        `when`(
            repo.findByDocumentNumber(anyString())
        ).thenReturn(Optional.empty())

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

        `when`(
            repo.findByDocumentNumber(anyString())
        ).thenReturn(Optional.empty())

        assertThrows<DataIntegrityViolationException> {
            adapter.save(account)
        }

    }

    @Test
    fun `Should not save an account when already exists`() {
        val account = AccountModel()

        `when`(
            repo.findByDocumentNumber(anyString())
        ).thenReturn(Optional.of(account.toEntity()))

        assertThrows<AccountAlreadyExistException> {
            adapter.save(account)
        }
    }


    @Test
    fun `Should find an account saved`() {
        val account = AccountModel(id = 12)

        `when`(
            repo.findById(anyLong())
        ).thenReturn(Optional.of(account.toEntity()))

        val accountFound = assertDoesNotThrow {
            adapter.findById(account.id)
        }

        assertEquals(account.id, accountFound.id)
        assertEquals(account.documentNumber, accountFound.documentNumber)
    }

    @Test
    fun `Should not find an account`() {
        val account = AccountModel(id = 12)

        `when`(
            repo.findById(anyLong())
        ).thenReturn(Optional.empty())

        assertThrows<AccountNotFoundException> {
            adapter.findById(account.id)
        }
    }
}