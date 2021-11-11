package com.technical.challenge.operator.service

import com.technical.challenge.APIException
import com.technical.challenge.AccountDoesntHaveBalanceException
import com.technical.challenge.account.processor.AccountProcessor
import com.technical.challenge.account.service.AccountService
import com.technical.challenge.operator.request.BillRequest
import com.technical.challenge.operator.request.TransferRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class UserAccountOperationService(@Autowired val accountService: AccountService) {

    @Transactional
    fun withdraw(userId: Long, value: BigDecimal) {
        try {
            accountService.save(
                AccountProcessor(
                    accountService
                        .getAccountAsAccountItself(userId)
                ).withdraw(value)
            )
        } catch (e: AccountDoesntHaveBalanceException) {
            throw AccountDoesntHaveBalanceException()
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun deposit(userId: Long, value: BigDecimal) {
        try {
            accountService.save(AccountProcessor(accountService.getAccountAsAccountItself(userId)).deposit(value))
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun transfer(fromUserId: Long, toUserId: Long, transferRequest: TransferRequest) {
        val fromAccount = accountService.getAccountAsAccountItself(fromUserId)
        val toAccount = accountService.getAccountAsAccountItself(toUserId)

        try {
            accountService.save(AccountProcessor(fromAccount).withdraw(transferRequest.transferedValue))
            accountService.save(AccountProcessor(toAccount).deposit(transferRequest.transferedValue))
        } catch (e: AccountDoesntHaveBalanceException) {
            throw AccountDoesntHaveBalanceException()
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    @Transactional
    fun payBill(userId: Long, billRequest: BillRequest) {
        try {
            this.accountService
                .save(
                    AccountProcessor(
                        accountService
                            .getAccountAsAccountItself(userId)
                    )
                        .withdraw(billRequest.value)
                )
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }
}
