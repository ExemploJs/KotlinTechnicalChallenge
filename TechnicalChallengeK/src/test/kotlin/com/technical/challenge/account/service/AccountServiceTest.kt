package com.technical.challenge.account.service

import com.technical.challenge.account.entity.Account
import com.technical.challenge.account.repository.AccountRepository
import com.technical.challenge.account.request.AccountCreationRequest
import com.technical.challenge.user.entity.User
import com.technical.challenge.user.repository.UserRepository
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

@ExtendWith(MockKExtension::class)
internal class AccountServiceTest {

    private val slot = slot<Account>()

    @InjectMockKs
    private lateinit var accountService: AccountService

    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var userRepository: UserRepository

    var id: Long = 0L

    private lateinit var request: AccountCreationRequest
    private lateinit var user: User
    private lateinit var actualAccount: Account

    @Test
    fun `creation of an account`() {
        givenAnId()
        givenARequest()
        whenTriedToFindUserById()
        whenTriedToSave()
        whenCreating()
        thenShouldHaveSaved()
        thenShouldConfirmAllCallsOfAccountRepository()
        thenShouldHaveSavedWithSameRequestValues()
    }

    @Test
    fun `inactivation of an account`() {
        givenAnId()
        whenTriedToFindAccountByUserId()
        whenTriedToSave()
        whenInactivating()
        thenShouldVerifyAfterFinding()
        thenShouldHaveSaved()
        thenShouldConfirmAllCallsOfAccountRepository()
        thenShouldHaveConfirmTheInactivation()
    }



    private fun thenShouldVerifyAfterFinding() {
        verify(exactly = 1) { accountRepository.findByUserId(id) }
    }

    private fun thenShouldHaveConfirmTheInactivation() {
        assertFalse(actualAccount.active)
    }

    private fun whenInactivating() {
        accountService.inactivate(id)
    }

    private fun givenARequest() {
        request = AccountCreationRequest(
            "12546",
            "5452", "1", "1", BigDecimal("1500")
        )
    }

    private fun givenAnId() {
        id = 1L
    }

    private fun thenShouldHaveSavedWithSameRequestValues() {
        assertEquals(request.accountNumber, actualAccount.accountNumber)
        assertEquals(request.agency, actualAccount.agency)
        assertEquals(request.balance, actualAccount.balance)
        assertEquals(request.accountDigit, actualAccount.accountDigit)
        assertEquals(request.agencyDigit, actualAccount.agencyDigit)
    }

    private fun whenTriedToSave() {
        every { accountRepository.save(capture(slot)) } answers {
            actualAccount = slot.captured
            slot.captured
        }
    }

    private fun whenTriedToFindUserById() {
        val parsed = Date.from(LocalDate.parse("2021-11-12").atStartOfDay(ZoneId.systemDefault()).toInstant())

        user = User(1, "junior", parsed)
        every { userRepository.findById(id) } answers {
            Optional.of(User(1, "junior", parsed))
        }
    }

    private fun whenTriedToFindAccountByUserId() {
        val parsed = Date.from(LocalDate.parse("2021-11-12").atStartOfDay(ZoneId.systemDefault()).toInstant())

        user = User(1, "junior", parsed)

        every { accountRepository.findByUserId(id) } answers {
            Optional.of(
                Account(
                    1, "14145", "4145", user, "4", "2",
                    BigDecimal("4340"), true
                )
            )
        }
    }

    private fun thenShouldHaveSaved() {
        verify(exactly = 1) { accountRepository.save(any()) }
    }

    private fun thenShouldConfirmAllCallsOfAccountRepository() {
        confirmVerified(accountRepository)
    }

    private fun whenCreating() {
        accountService.create(id, request)
    }
}