package com.technical.challenge.history.consumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.technical.challenge.APIException
import com.technical.challenge.account.repository.AccountRepository
import com.technical.challenge.history.entity.History
import com.technical.challenge.history.repository.HistoryRepository
import com.technical.challenge.history.request.HistoryRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class HistoryConsumer @Autowired constructor(
    val historyRepository: HistoryRepository,
    val accountRepository: AccountRepository
) {

    @KafkaListener(topics = ["history-topic"])
    @Transactional
    fun listener(data: String) {
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val historyRequest = mapper.readValue<HistoryRequest>(data)

            historyRepository.save(getHistory(historyRequest))
        } catch (e: Exception) {
            throw APIException(e.message!!)
        }
    }

    private fun getHistory(historyRequest: HistoryRequest) = History(
        null, historyRequest.operation, historyRequest.message,
        accountRepository.findById(historyRequest.accountId).get(), historyRequest.currentBalance
    )
}
