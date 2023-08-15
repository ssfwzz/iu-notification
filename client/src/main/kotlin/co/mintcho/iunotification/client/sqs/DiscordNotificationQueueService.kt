package co.mintcho.iunotification.client.sqs

import co.mintcho.iunotification.domain.notification.model.event.NotificationCreatedEvent
import co.mintcho.iunotification.domain.notification.service.NotificationQueueService
import com.fasterxml.jackson.databind.ObjectMapper
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest
import java.util.UUID

@Service
class DiscordNotificationQueueService(
    private val sqsAsyncClient: SqsAsyncClient,
    private val objectMapper: ObjectMapper
) : NotificationQueueService {
    private val log = KotlinLogging.logger {}

    override fun send(event: NotificationCreatedEvent) {
        log.info { "sending a message to the discord notification queue.: $event" }

        sqsAsyncClient.sendMessage(
            SendMessageRequest.builder()
                .queueUrl(DISCORD_NOTIFICATION_QUEUE)
                .messageBody(objectMapper.writeValueAsString(event))
                .messageGroupId("discord")
                .messageDeduplicationId(UUID.randomUUID().toString())
                .build()
        )
    }
}
