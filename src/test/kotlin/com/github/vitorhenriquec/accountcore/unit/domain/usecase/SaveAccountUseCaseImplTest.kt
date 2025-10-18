package com.github.vitorhenriquec.accountcore.unit.domain.usecase

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.domain.usecase.SaveAccountUseCaseImpl
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import com.github.vitorhenriquec.accountcore.infrastructure.util.toEntity
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException

class SaveAccountUseCaseImplTest {

    private val adapter = mock<AccountDatabaseAdapter>()

    private val useCase = SaveAccountUseCaseImpl(
        adapter
    )

    @Test
    fun `Should save an account`() {
        val documentNumber = "313131313131313"
        val account = AccountModel()
        account.id = 12L
        account.documentNumber = documentNumber

        `when`(
            adapter.save(account)
        ).thenReturn(account)

        val idSaved = assertDoesNotThrow { useCase.save(documentNumber) }
        Assertions.assertEquals(account.id, idSaved)
    }

    @Test
    fun `Should not save an account`() {
        val documentNumber = "313131313131313"
        val account = AccountModel()
        account.id = 12L
        account.documentNumber = documentNumber

        `when`(
            adapter.save(account)
        ).thenThrow(
            DataIntegrityViolationException("Column unique")
        )

        assertThrows<DataIntegrityViolationException> {
            useCase.save(documentNumber)
        }
    }

}