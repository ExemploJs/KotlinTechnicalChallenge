package com.technical.challenge.account.entity

import com.technical.challenge.user.entity.User
import java.math.BigDecimal
import javax.persistence.*

@Entity
data class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id : Long?,
    val accountNumber : String,
    val agency : String,

    @OneToOne
    val user : User?,

    val accountDigit : String?,
    val agencyDigit : String?,
    val balance : BigDecimal,
    var active : Boolean)
