package com.github.vitorhenriquec.accountcore.unit.infrastructure.adapter

import com.github.vitorhenriquec.accountcore.domain.model.AccountModel
import com.github.vitorhenriquec.accountcore.infrastructure.adapters.AccountDatabaseAdapter
import com.github.vitorhenriquec.accountcore.infrastructure.repositories.AccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.dao.DataIntegrityViolationException


class AccountDatabaseAdapterTest {
    private val repo = mock<AccountRepository>()
    private val adapter = AccountDatabaseAdapter(repo)

    @Test
    fun `Should save an account`() {
        val account = AccountModel()

        val accountResult = adapter.save()
        Assertions.assertEquals(account.id, accountResult.id)
    }

    @Test
    fun `Should not save an account`() {
        val account = AccountModel()

        `when`(
            repo.save(any())
        ).thenThrow(DataIntegrityViolationException("duplicate key"))

        val accountResult = adapter.save()
        Assertions.assertEquals(account.id, accountResult.id)
    }
}