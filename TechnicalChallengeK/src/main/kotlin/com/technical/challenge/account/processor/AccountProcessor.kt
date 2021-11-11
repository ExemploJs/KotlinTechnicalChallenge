package com.technical.challenge.account.processor

import com.technical.challenge.AccountDoesntHaveBalanceException
import com.technical.challenge.account.entity.Account
import java.math.BigDecimal

class AccountProcessor(private val account : Account) {

    fun withdraw(value : BigDecimal) : Account {
        when(isBalanceGreaterThanZero() && isBalanceGreaterThanWithdrawValue(value)) {

            true -> return account.copy(
                id = account.id,
                accountNumber = account.accountNumber,
                agency = account.agency,
                user = account.user,
                accountDigit = account.accountDigit,
                agencyDigit = account.agencyDigit,
                balance = account.balance.subtract(value),
                active = true
            )
            false ->  throw AccountDoesntHaveBalanceException()
        }
    }

    fun deposit(value : BigDecimal) : Account {
        return account.copy(
            id = account.id,
            accountNumber = account.accountNumber,
            agency = account.agency,
            user = account.user,
            accountDigit = account.accountDigit,
            agencyDigit = account.agencyDigit,
            balance = account.balance.add(value),
            active = true
        )
    }

    private fun isBalanceGreaterThanWithdrawValue(withdrawValue: BigDecimal): Boolean = account.balance > withdrawValue

    private fun isBalanceGreaterThanZero(): Boolean = account.balance > BigDecimal.ZERO

}