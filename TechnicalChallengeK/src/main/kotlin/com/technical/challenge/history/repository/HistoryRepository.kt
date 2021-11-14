package com.technical.challenge.history.repository

import com.technical.challenge.history.entity.History
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository : CrudRepository<History, Long> {

    @Query("select h from History h where h.account.id = ?1")
    fun findByAccountId(accountId : Long) : List<History>
}
