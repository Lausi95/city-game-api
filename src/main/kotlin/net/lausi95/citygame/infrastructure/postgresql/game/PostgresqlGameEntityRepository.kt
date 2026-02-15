package net.lausi95.citygame.infrastructure.postgresql.game

import org.springframework.data.jpa.repository.JpaRepository

interface PostgresqlGameEntityRepository : JpaRepository<PostgresqlGameEntity, String> {

    fun findByIdAndTenant(id: String, tenant: String): PostgresqlGameEntity?

    fun existsByTitleAndTenant(title: String, tenant: String): Boolean
}