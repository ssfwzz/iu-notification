package co.mintcho.iunotification.domain.notification.model.entity

import co.mintcho.iunotification.domain.notification.model.NotificationType
import java.time.LocalDateTime

class Notification(
    val id: Long = 0L,
    val externalId: String,
    val type: NotificationType,
    val payload: String,
    var published: Boolean = false,
    var createdDate: LocalDateTime? = LocalDateTime.now(),
    var modifiedDate: LocalDateTime? = createdDate
) {
    fun complete() {
        this.published = true
    }
}
