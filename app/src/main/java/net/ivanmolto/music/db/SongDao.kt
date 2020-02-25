/*
 * Copyright (C) 2020 Ivan Molto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.ivanmolto.music.db

import android.util.SparseIntArray
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
// import net.ivanmolto.music.testing.OpenForTesting
import net.ivanmolto.music.vo.Song
import net.ivanmolto.music.vo.SongSearchResult

/**
 * Interface for database access on Song related operations.
 */
@Dao
// @OpenForTesting
abstract class SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertSongs(repositories: List<Song>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(result: SongSearchResult)

    @Query("SELECT * FROM SongSearchResult WHERE `query` = :query")
    abstract fun search(query: String): LiveData<SongSearchResult?>

    fun loadOrdered(songIds: List<Int>): LiveData<List<Song>> {
        val order = SparseIntArray()
        songIds.withIndex().forEach {
            order.put(it.value, it.index)
        }
        return loadById(songIds).map { repositories ->
            repositories.sortedWith(compareBy { order.get(it.id) })
        }
    }

    @Query("SELECT * FROM Song WHERE id in (:songIds)")
    protected abstract fun loadById(songIds: List<Int>): LiveData<List<Song>>

    @Query("SELECT * FROM SongSearchResult WHERE `query` = :query")
    abstract fun findSearchResult(query: String): SongSearchResult?

    @Query("SELECT * FROM Song WHERE id = :id")
    abstract fun getSong(id: Int): LiveData<Song>
}
