package co.mintcho.iunotification.consumer.sqs.listener

import co.mintcho.iunotification.client.sqs.DISCORD_NOTIFICATION_QUEUE
import co.mintcho.iunotification.consumer.sqs.message.DiscordNotificationMessage
import co.mintcho.iunotification.consumer.sqs.service.DiscordNotificationService
import io.awspring.cloud.sqs.annotation.SqsListener
import org.springframework.stereotype.Component

@Component
class DiscordNotificationSqsListener(private val discordNotificationService: DiscordNotificationService) {
    @SqsListener(value = [DISCORD_NOTIFICATION_QUEUE])
    fun listen(message: DiscordNotificationMessage) {
        discordNotificationService.send(message)
    }
}
