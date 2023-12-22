package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import me.sujanpoudel.mputils.paths.appDataDirectory
import me.sujanpoudel.mputils.paths.utils.div
import me.sujanpoudel.playdeals.common.Constants
import me.sujanpoudel.playdeals.common.SqliteDatabase
import me.sujanpoudel.playdeals.common.BuildKonfig

actual fun createSqlDriver(): SqlDriver {

  val path = appDataDirectory(BuildKonfig.PACKAGE_NAME) / Constants.DATABASE_NAME

  return JdbcSqliteDriver(
    url = "jdbc:sqlite:${path}",
    schema = SqliteDatabase.Schema.synchronous(),
    migrateEmptySchema = true,
  )
}
