package com.technical.challenge.history.producer

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.technical.challenge.APIException
import com.technical.challenge.history.request.HistoryRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class HistoryProducer(
    @Value("\${topic.name.producer}") val topicName: String,
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun send(request: HistoryRequest) {
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
            kafkaTemplate.send(topicName, mapper.writeValueAsString(request))
        } catch (e : Exception) {
            throw APIException()
        }
    }
}