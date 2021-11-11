package com.technical.challenge.account.service

import com.technical.challenge.APIException
import com.technical.challenge.AccountNotFoundException
import com.technical.challenge.UserNotFoundException
import com.technical.challenge.account.entity.Account
import com.technical.challenge.account.repository.AccountRepository
import com.technical.challenge.account.request.AccountCreationRequest
import com.technical.challenge.account.response.AccountResponse
import com.technical.challenge.user.entity.User
import com.technical.challenge.user.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(val repository: AccountRepository, val userRepository: UserRepository) {

    @Transactional
    fun create(id: Long, request: AccountCreationRequest) {
        userRepository
            .findById(id)
            .orElseThrow { UserNotFoundException() }.let {
                try {
                    repository.save(getAccount(request, it))
                } catch (e: Exception) {
                    throw APIException()
                }
            }
    }

    @Transactional
    fun save(account: Account) {
        try {
            repository.save(account)
        } catch(e : Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun inactivate(id: Long) {
        repository
            .findByUserId(id)
            .orElseThrow { UserNotFoundException() }.let {
                try {
                    it.active = false
                    repository.save(it)
                } catch (e: Exception) {
                    throw APIException()
                }
            }
    }

    fun getAccount(userId: Long): AccountResponse = repository.findByUserId(userId)
        .map { account -> getAccountResponse(account) }
        .orElseThrow { AccountNotFoundException() }

    fun getAccountAsAccountItself(userId: Long): Account = repository.findByUserId(userId)
        .orElseThrow { AccountNotFoundException() }

    private fun getAccount(request: AccountCreationRequest, user: User) =
        Account(
            null, request.accountNumber,
            request.agency, user,
            request.accountDigit,
            request.agencyDigit,
            request.balance, true
        )

    private fun getAccountResponse(account: Account) =
        AccountResponse(
            account.accountNumber,
            account.agency,
            account.accountDigit,
            account.agencyDigit,
            account.balance, account.active
        )


}
