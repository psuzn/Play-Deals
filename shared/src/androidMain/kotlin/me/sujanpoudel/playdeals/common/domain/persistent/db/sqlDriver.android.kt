package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase
import me.sujanpoudel.playdeals.common.applicationContext

actual fun createSqlDriver(): SqlDriver = AndroidSqliteDriver(
  schema = SqliteDatabase.Schema.synchronous(),
  context = applicationContext,
  name = Constants.DATABASE_NAME,
)
