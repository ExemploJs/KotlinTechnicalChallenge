package com.technical.challenge.history.request

import com.technical.challenge.history.data.Operation
import java.math.BigDecimal

data class HistoryRequest(
    val operation: Operation,
    val message: String,
    val accountId: Long,
    val currentBalance: BigDecimal
)
