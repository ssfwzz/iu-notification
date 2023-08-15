package co.mintcho.iunotification.domain.notification.repository

import co.mintcho.iunotification.domain.common.CrudRepository
import co.mintcho.iunotification.domain.notification.model.entity.Notification

interface NotificationRepository : CrudRepository<Notification, Long> {
    fun existsByExternalId(externalId: String): Boolean
}
