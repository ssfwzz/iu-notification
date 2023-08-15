package co.mintcho.iunotification.domain.notification.listener

import co.mintcho.iunotification.domain.notification.model.event.NotificationCreatedEvent
import co.mintcho.iunotification.domain.notification.service.NotificationQueueService
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationEventListener(private val notificationQueueService: NotificationQueueService) {
    @Async
    @TransactionalEventListener
    fun handle(event: NotificationCreatedEvent) {
        notificationQueueService.send(event)
    }
}
