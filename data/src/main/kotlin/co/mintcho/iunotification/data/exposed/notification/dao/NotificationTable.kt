package co.mintcho.iunotification.data.exposed.notification.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.json.json
import java.time.LocalDateTime

object NotificationTable : LongIdTable(name = "notification") {
    val externalId = varchar(name = "external_id", length = 255).index()
    val type = varchar(name = "type", length = 255)
    val payload = json(name = "payload", serialize = { it }, deserialize = { it })
    val published = bool(name = "published")
    val createdDate = datetime(name = "created_date").nullable().clientDefault { LocalDateTime.now() }
    val modifiedDate = datetime(name = "modified_date").nullable().clientDefault { LocalDateTime.now() }
}
