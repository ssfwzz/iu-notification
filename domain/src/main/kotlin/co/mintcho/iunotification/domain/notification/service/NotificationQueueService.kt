package co.mintcho.iunotification.domain.notification.service

import co.mintcho.iunotification.domain.notification.model.event.NotificationCreatedEvent

interface NotificationQueueService {
    fun send(event: NotificationCreatedEvent)
}
