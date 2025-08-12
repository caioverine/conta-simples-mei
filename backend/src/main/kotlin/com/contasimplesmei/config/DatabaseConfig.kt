package com.contasimplesmei.config

import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import javax.sql.DataSource

@Configuration
class DatabaseConfig(private val env: Environment) {
    @Bean
    @Primary
    fun dataSource(): DataSource {
        val databaseUrl = env.getProperty("DATABASE_URL")

        return if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
            // Railway format: postgresql://user:pass@host:port/db
            // Convert to: jdbc:postgresql://host:port/db
            val jdbcUrl = databaseUrl.replace("postgresql://", "jdbc:postgresql://")

            DataSourceBuilder.create()
                .url(jdbcUrl)
                .driverClassName("org.postgresql.Driver")
                .build()
        } else {
            // Use standard configuration for local/other environments
            DataSourceBuilder.create()
                .url(env.getProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/contasimplesmei"))
                .username(env.getProperty("spring.datasource.username", "postgres"))
                .password(env.getProperty("spring.datasource.password", "postgres"))
                .driverClassName("org.postgresql.Driver")
                .build()
        }
    }
}