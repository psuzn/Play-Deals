package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase

actual fun createSqlDriver(): SqlDriver = NativeSqliteDriver(
  schema = SqliteDatabase.Schema.synchronous(),
  name = Constants.DATABASE_NAME,
)
