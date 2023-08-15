package co.mintcho.iunotification.domain.notification.service

import co.mintcho.iunotification.domain.notification.model.entity.Notification
import co.mintcho.iunotification.domain.notification.model.event.NotificationCreatedEvent
import co.mintcho.iunotification.domain.notification.model.request.NotificationRequest
import co.mintcho.iunotification.domain.notification.repository.NotificationRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationCommandService(
    private val notificationRepository: NotificationRepository,
    private val objectMapper: ObjectMapper,
    private val applicationEventPublisher: ApplicationEventPublisher
) {
    @Transactional
    fun save(request: NotificationRequest) {
        val notification = notificationRepository.save(
            Notification(
                externalId = request.externalId,
                type = request.type,
                payload = objectMapper.writeValueAsString(request.payload)
            )
        )

        applicationEventPublisher.publishEvent(
            NotificationCreatedEvent(
                id = notification.id,
                externalId = notification.externalId,
                type = notification.type,
                payload = notification.payload
            )
        )
    }

    @Transactional
    fun complete(id: Long) {
        val notification = notificationRepository.findById(id) ?: return

        notification.complete()

        notificationRepository.save(notification)
    }
}
