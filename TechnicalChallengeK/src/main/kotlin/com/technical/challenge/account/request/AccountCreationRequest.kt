package com.technical.challenge.account.request

import java.math.BigDecimal

data class AccountCreationRequest(val accountNumber : String,
                                  val agency : String,
                                  val accountDigit : String?,
                                  val agencyDigit : String?,
                                  val balance : BigDecimal)