package com.technical.challenge.account.response

import java.math.BigDecimal

data class AccountResponse(
    val accountNumber: String,
    val agency: String,
    val accountDigit: String?,
    val agencyDigit: String?,
    val balance: BigDecimal,
    val active: Boolean
)
