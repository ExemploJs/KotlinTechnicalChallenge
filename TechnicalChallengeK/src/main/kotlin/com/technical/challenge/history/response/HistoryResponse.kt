package com.technical.challenge.history.response

import java.math.BigDecimal

data class HistoryResponse(val operation : String, val message : String, val currentBalance : BigDecimal)
