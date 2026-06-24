package com.example.firstproject.data

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask



@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null
    )
    return requireNotNull(documentDirectory?.path)
}

fun getDatabaseBuilder(): RoomDatabase.Builder<NewsDatabase> {
    val dbFilePath = documentDirectory() + "/" + NewsDatabase.DB_NAME
    return Room.databaseBuilder<NewsDatabase>(name = dbFilePath)
}

fun NewsDatabase.Companion.build(): NewsDatabase =
    getDatabaseBuilder()
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(kotlinx.coroutines.Dispatchers.Default)
        .build()