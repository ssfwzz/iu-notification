package co.mintcho.iunotification.domain.notification.model.event

import co.mintcho.iunotification.domain.notification.model.NotificationType

data class NotificationCreatedEvent(
    val id: Long,
    val externalId: String,
    val type: NotificationType,
    val payload: String
)
