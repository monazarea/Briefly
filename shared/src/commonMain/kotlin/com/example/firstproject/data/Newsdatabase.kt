package com.example.firstproject.data


import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor

/**
 * Room database root. Uses Room's official Kotlin Multiplatform support
 * (Room 2.7+), which generates actual implementations per-platform via the
 * `@ConstructedBy` mechanism instead of platform-specific builder code in
 * each source set.
 */
@Database(
    entities = [FavoriteArticleEntity::class],
    version = 1,
    exportSchema = true
)
@ConstructedBy(NewsDatabaseConstructor::class)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DB_NAME = "news_app.db"
    }
}

/**
 * Expect object implemented per-platform (see androidMain/iosMain) to satisfy
 * Room's KMP compiler-generated constructor mechanism.
 */
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object NewsDatabaseConstructor : RoomDatabaseConstructor<NewsDatabase> {
    override fun initialize(): NewsDatabase
}