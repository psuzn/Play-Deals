package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase

actual fun createSqlDriver(): SqlDriver = JdbcSqliteDriver(
  url = "jdbc:sqlite:${Constants.DATABASE_NAME}",
  schema = SqliteDatabase.Schema.synchronous(),
  migrateEmptySchema = true,
)
