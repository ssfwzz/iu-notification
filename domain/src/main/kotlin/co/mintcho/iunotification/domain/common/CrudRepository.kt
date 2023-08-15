package co.mintcho.iunotification.domain.common

interface CrudRepository<Entity, Id> {
    fun save(entity: Entity): Entity
    fun saveAll(entities: List<Entity>): List<Entity>
    fun delete(entity: Entity)
    fun deleteAll(entities: List<Entity>)
    fun deleteById(id: Id)
    fun deleteByIdIn(ids: List<Id>)
    fun findById(id: Id): Entity?
}
