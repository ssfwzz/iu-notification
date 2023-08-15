package co.mintcho.iunotification.domain.notification.service

import co.mintcho.iunotification.domain.notification.repository.NotificationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class NotificationQueryService(private val notificationRepository: NotificationRepository) {
    @Transactional
    fun existsByExternalId(externalId: String): Boolean {
        return notificationRepository.existsByExternalId(externalId)
    }
}
