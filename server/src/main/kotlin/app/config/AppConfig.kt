package app.config

import app.entity.Models
import io.requery.sql.KotlinConfiguration
import io.requery.sql.KotlinEntityDataStore
import org.apache.commons.dbcp.BasicDataSource
import javax.sql.DataSource

/**
 *
 * @author nsoushi
 */
class AppConfig {

    fun realDataSource(): DataSource {
        val dataSource = BasicDataSource()
        dataSource.driverClassName = "com.mysql.cj.jdbc.Driver"
        dataSource.url = "jdbc:mysql://mysql:3306/todo?useUnicode=true&characterEncoding=UTF-8&connectionCollation=utf8mb4_general_ci&useSSL=false"
        dataSource.username = "root"
        dataSource.password = ""
        return dataSource
    }

    fun kotlinEntityDataStore(): KotlinEntityDataStore<Any> {
        val configuration = KotlinConfiguration(
                dataSource = realDataSource(),
                model = Models.KT,
                statementCacheSize = 0,
                useDefaultLogging = true)
        return KotlinEntityDataStore(configuration)
    }
}