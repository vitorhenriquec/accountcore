package com.github.vitorhenriquec.accountcore.infrastructure.configurations

import com.zaxxer.hikari.HikariDataSource
import jakarta.annotation.PostConstruct
import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConditionalOnProperty(prefix = "spring.flyway", name = ["enabled"], havingValue = "true")
@ConditionalOnClass(value = [Flyway::class, HikariDataSource::class, DataSourceProperties::class])
class DatabaseMigration(
        private val dataSourceProperties: DataSourceProperties
) {

    @PostConstruct
    fun execute() {
        val migrationDataSource = HikariDataSource()
        migrationDataSource.username = dataSourceProperties.username
        migrationDataSource.password = dataSourceProperties.password
        migrationDataSource.driverClassName = dataSourceProperties.driverClassName
        migrationDataSource.jdbcUrl = dataSourceProperties.url

        val flyway = Flyway.configure()
                .locations("classpath:db/migration")
                .dataSource(migrationDataSource)
                .load()

        flyway.migrate()
        migrationDataSource.close()
    }

}
