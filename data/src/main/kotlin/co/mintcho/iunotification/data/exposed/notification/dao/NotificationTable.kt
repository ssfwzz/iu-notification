package co.mintcho.iunotification.data.exposed.notification.dao

import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.json.json
import java.time.LocalDateTime

object NotificationTable : LongIdTable(name = "notification") {
    val externalId = varchar(name = "external_id", length = 255).index()
    val type = varchar(name = "type", length = 255)
    val payload = json(name = "payload", serialize = { it }, deserialize = { it })
    var published = bool(name = "published")
    var createdDate = datetime(name = "created_date").nullable().clientDefault { LocalDateTime.now() }
    var modifiedDate = datetime(name = "modified_date").nullable().clientDefault { LocalDateTime.now() }
}
