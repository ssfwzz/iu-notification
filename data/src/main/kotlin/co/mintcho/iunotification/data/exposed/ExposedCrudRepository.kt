package co.mintcho.iunotification.data.exposed

import co.mintcho.iunotification.domain.common.CrudRepository
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.statements.UpdateStatement
import org.jetbrains.exposed.sql.update

interface ExposedCrudRepository<Entity, Id : Comparable<Id>> : CrudRepository<Entity, Id> {
    val table: Table
    fun idColumn(): Column<EntityID<Id>>
    fun id(entity: Entity): Id?
    fun toInsertStatement(entity: Entity): Table.(InsertStatement<Number>) -> Unit
    fun toUpdateStatement(entity: Entity): Table.(UpdateStatement) -> Unit
    fun toEntity(row: ResultRow): Entity

    override fun save(entity: Entity): Entity {
        if (id(entity) == null) {
            return toEntity(table.insert(toInsertStatement(entity)).resultedValues!!.single())
        }

        return table.update(where = { idColumn() eq id(entity) }, body = toUpdateStatement(entity)).let { entity }
    }

    override fun saveAll(entities: List<Entity>): List<Entity> {
        return entities.map { save(it) }
    }

    override fun delete(entity: Entity) {
        deleteById(id(entity)!!)
    }

    override fun deleteAll(entities: List<Entity>) {
        entities.forEach { delete(it) }
    }

    override fun deleteById(id: Id) {
        table.deleteWhere { idColumn() eq id }
    }

    override fun deleteByIdIn(ids: List<Id>) {
        ids.forEach { deleteById(it) }
    }

    override fun findById(id: Id): Entity? {
        return table.select { idColumn() eq id }.singleOrNull()?.let { toEntity(it) }
    }
}
