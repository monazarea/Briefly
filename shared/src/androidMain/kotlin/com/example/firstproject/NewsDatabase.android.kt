package com.example.firstproject.data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers



/**
 * Builds the platform-specific Room database for Android.
 * Called from the Koin DatabaseModule with the Android application Context.
 */
fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<NewsDatabase> {
    val dbFile = context.getDatabasePath(NewsDatabase.DB_NAME)
    return Room.databaseBuilder<NewsDatabase>(
        context = context,
        name = dbFile.absolutePath
    )
}

fun NewsDatabase.Companion.build(context: Context): NewsDatabase =
    getDatabaseBuilder(context)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()