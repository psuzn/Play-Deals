package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase

actual fun createSqlDriver(): SqlDriver = JdbcSqliteDriver("jdbc:sqlite:${Constants.DATABASE_NAME}").apply {
  SqliteDatabase.Schema.create(this)
}
