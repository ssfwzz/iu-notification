package co.mintcho.iunotification.data.exposed.notification.repository

import co.mintcho.iunotification.data.exposed.ExposedCrudRepository
import co.mintcho.iunotification.data.exposed.notification.dao.NotificationTable
import co.mintcho.iunotification.domain.notification.model.NotificationType
import co.mintcho.iunotification.domain.notification.model.entity.Notification
import co.mintcho.iunotification.domain.notification.repository.NotificationRepository
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.springframework.stereotype.Repository

@Repository
class NotificationRepositoryImpl : NotificationRepository, ExposedCrudRepository<Notification, Long> {
    override val table = NotificationTable

    override fun idColumn() = table.id

    override fun id(entity: Notification) = if (entity.id == 0L) null else entity.id

    override fun toInsertStatement(entity: Notification): Table.(InsertStatement<Number>) -> Unit = {
        it[table.externalId] = entity.externalId
        it[table.type] = entity.type.name
        it[table.payload] = entity.payload
        it[table.published] = entity.published
        it[table.createdDate] = entity.createdDate
        it[table.modifiedDate] = entity.modifiedDate
    }

    override fun toUpdateStatement(entity: Notification): Table.(UpdateStatement) -> Unit = {
        it[table.externalId] = entity.externalId
        it[table.type] = entity.type.name
        it[table.payload] = entity.payload
        it[table.published] = entity.published
        it[table.createdDate] = entity.createdDate
        it[table.modifiedDate] = entity.modifiedDate
    }

    override fun toEntity(row: ResultRow) = Notification(
        id = row[table.id].value,
        externalId = row[table.externalId],
        type = NotificationType.valueOf(row[table.type]),
        payload = row[table.payload],
        published = row[table.published],
        createdDate = row[table.createdDate],
        modifiedDate = row[table.modifiedDate]
    )

    override fun existsByExternalId(externalId: String): Boolean {
        return table.select { table.externalId eq externalId }.any()
    }
}
