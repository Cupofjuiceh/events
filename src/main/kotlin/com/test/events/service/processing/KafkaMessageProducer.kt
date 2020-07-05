package com.test.events.service.processing

import com.test.events.model.user.User
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kafka.sender.KafkaSender
import reactor.kafka.sender.SenderRecord

@Component
class KafkaMessageProducer (
        private val kafkaSender: KafkaSender<String, Any>
) {
    @Value("\${spring.kafka.email.verification-code-topic}")
    lateinit var emailVerificationTopic: String

    fun sendEmailVerificationCode(user: User, verificationCode: Int) {
        val message = mutableMapOf<String, String>()

        message["userId"] = user.id.toString()
        message["email"] = user.email
        message["fullName"] = user.email
        message["verificationCode"] = verificationCode.toString()

    }

    private fun sendData(topic: String, value: Any) =
            kafkaSender
                    .send(Mono.just(SenderRecord.create(ProducerRecord(topic, value), value.javaClass.simpleName)))
                    .doOnError { println("Send ${value.javaClass.simpleName} failed")}
                    .subscribe { r -> println("Message ${r.correlationMetadata()} sent successfully")}
}