package com.technical.challenge.history.entity

import com.technical.challenge.account.entity.Account
import com.technical.challenge.history.data.Operation
import java.math.BigDecimal
import javax.persistence.*


@Entity
data class History(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val operation: Operation,
    val message: String,

    @ManyToOne
    val account: Account,
    val currentBalance: BigDecimal
)