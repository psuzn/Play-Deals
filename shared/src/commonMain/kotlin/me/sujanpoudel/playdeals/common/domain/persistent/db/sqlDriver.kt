package me.sujanpoudel.playdeals.common.domain.persistent.db

import app.cash.sqldelight.db.SqlDriver

expect fun createSqlDriver(): SqlDriver
