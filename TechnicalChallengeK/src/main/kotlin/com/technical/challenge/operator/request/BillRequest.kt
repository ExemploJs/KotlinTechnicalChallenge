package com.technical.challenge.operator.request

import java.math.BigDecimal


data class BillRequest(val barCode: String, val description: String, val value: BigDecimal)
