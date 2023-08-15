package co.mintcho.iunotification.domain.notification.model.request

import co.mintcho.iunotification.domain.notification.model.NotificationType

data class NotificationRequest(
    val externalId: String,
    val type: NotificationType,
    val payload: Any
)
