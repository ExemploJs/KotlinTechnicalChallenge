package com.technical.challenge.config

import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.*
import org.springframework.kafka.support.serializer.JsonSerializer


@EnableKafka
@Configuration
class KafkaConfig(@Value("\${topic.name.producer}") val topicName : String) {

    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        val config: MutableMap<String, Any> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka:9092"
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        return DefaultKafkaProducerFactory(config)
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, String?>? {
        val config: MutableMap<String, Any> = HashMap()
        config[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "kafka:9092"
        config[ConsumerConfig.GROUP_ID_CONFIG] = "configure-me_group-id"
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringDeserializer"
        config[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        config[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        return DefaultKafkaConsumerFactory(config)
    }

    @Bean
    fun kafkaTemplate() = KafkaTemplate(producerFactory())

    @Bean
    fun adviceTopic() = NewTopic(topicName, 3, 1.toShort())
}