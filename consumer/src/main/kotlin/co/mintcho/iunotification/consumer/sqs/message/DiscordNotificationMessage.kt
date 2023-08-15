package co.mintcho.iunotification.consumer.sqs.message

import co.mintcho.iunotification.domain.notification.model.NotificationType

data class DiscordNotificationMessage(
    val id: Long,
    val externalId: String,
    val type: NotificationType,
    val payload: String
)
