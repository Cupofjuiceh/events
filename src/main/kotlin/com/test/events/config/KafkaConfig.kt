package com.test.events.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.support.serializer.JsonSerializer
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderOptions
import java.util.*

@Configuration
@EnableKafka
class KafkaConfig {
    @Value("\${spring.kafka.producer.bootstrap-servers}")
    lateinit var bootstrapServer: String

    @Value("\${spring.kafka.producer.client-id}")
    lateinit var clientId: String

    @Bean
    fun senderOptions(): SenderOptions<String, Any> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapServer
        props[ProducerConfig.CLIENT_ID_CONFIG] = clientId
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return SenderOptions.create(props)
    }

    @Bean
    fun kafkaSender(): KafkaSender<String, Any> {
        return KafkaSender.create(senderOptions())
    }
}
