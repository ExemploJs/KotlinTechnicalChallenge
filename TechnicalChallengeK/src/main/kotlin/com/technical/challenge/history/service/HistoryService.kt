package com.technical.challenge.history.service

import com.technical.challenge.history.repository.HistoryRepository
import com.technical.challenge.history.response.HistoryResponse
import org.springframework.stereotype.Service

@Service
class HistoryService(val repository: HistoryRepository) {

    fun findHistoriesByAccountId(accountId: Long) = repository
        .findByAccountId(accountId)
        .map { h -> HistoryResponse(h.operation.operationName, h.message, h.currentBalance) }
        .toList()
}
