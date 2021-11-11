package com.technical.challenge.account.repository

import com.technical.challenge.account.entity.Account
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccountRepository : CrudRepository<Account, Long> {

    @Query(value = "select c from Account c where c.user.id = ?1")
    fun findByUserId(userId : Long) : Optional<Account>
}
