package com.example.firstproject.data


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    /**
     * Reactive stream of all favorites, ordered most-recently-saved first.
     * Room automatically re-emits whenever the table changes, which is what
     * powers the Favorites screen's "Observe database changes using Flow"
     * requirement.
     */
    @Query("SELECT * FROM favorite_articles ORDER BY publishedAt DESC")
    fun observeAll(): Flow<List<FavoriteArticleEntity>>

    /**
     * IGNORE on conflict means inserting an article that's already saved
     * (same primary key / url) is a silent no-op instead of a crash or a
     * duplicate row — this is the data-layer safety net for duplicate
     * prevention described in the domain's AddFavoriteUseCase.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entity: FavoriteArticleEntity)

    @Query("DELETE FROM favorite_articles WHERE id = :articleId")
    suspend fun deleteById(articleId: String)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_articles WHERE id = :articleId)")
    suspend fun exists(articleId: String): Boolean
}