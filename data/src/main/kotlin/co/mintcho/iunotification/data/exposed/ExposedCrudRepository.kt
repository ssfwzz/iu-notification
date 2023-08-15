package co.mintcho.iunotification.data.exposed

import co.mintcho.iunotification.domain.common.CrudRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.inList
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update
import org.springframework.transaction.annotation.Transactional

interface ExposedCrudRepository<Entity, Id : Comparable<Id>> : CrudRepository<Entity, Id> {
    val table: Table
    fun idColumn(): Column<EntityID<Id>>
    fun id(entity: Entity): Id?
    fun toInsertStatement(entity: Entity): Table.(InsertStatement<Number>) -> Unit
    fun toUpdateStatement(entity: Entity): Table.(UpdateStatement) -> Unit
    fun toEntity(row: ResultRow): Entity

    @Transactional
    override fun save(entity: Entity): Entity {
        val id = id(entity) ?: return toEntity(table.insert(toInsertStatement(entity)).resultedValues!!.single())
        return table.update(where = { idColumn() eq id }, body = toUpdateStatement(entity)).let { entity }
    }

    @Transactional
    override fun saveAll(entities: List<Entity>): List<Entity> {
        val toInsert = entities.filter { id(it) == null }
        val toUpdate = entities.filter { id(it) != null }
        val inserted = table.batchInsert(toInsert) { toInsertStatement(it) }.map { it }
        toUpdate.forEach { table.update(where = { idColumn() eq id(it) }, body = toUpdateStatement(it)) }
        return inserted.map { toEntity(it) } + toUpdate
    }

    @Transactional
    override fun delete(entity: Entity) {
        id(entity)?.let { deleteById(it) }
    }

    @Transactional
    override fun deleteAll(entities: List<Entity>) {
        deleteByIdIn(entities.mapNotNull { id(it) })
    }

    @Transactional
    override fun deleteById(id: Id) {
        table.deleteWhere { idColumn() eq id }
    }

    @Transactional
    override fun deleteByIdIn(ids: List<Id>) {
        if (ids.isNotEmpty()) {
            table.deleteWhere { idColumn() inList ids }
        }
    }

    @Transactional
    override fun findById(id: Id): Entity? {
        return table.select { idColumn() eq id }.singleOrNull()?.let { toEntity(it) }
    }
}
