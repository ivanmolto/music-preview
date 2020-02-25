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

package net.ivanmolto.music.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import net.ivanmolto.music.db.MusicDb
// import net.ivanmolto.music.testing.OpenForTesting
import net.ivanmolto.music.vo.Song
import net.ivanmolto.music.vo.SongSearchResult
import net.ivanmolto.music.vo.Resource
import net.ivanmolto.music.AppExecutors
import net.ivanmolto.music.api.ApiSuccessResponse
import net.ivanmolto.music.api.SongSearchResponse
import net.ivanmolto.music.api.TunesService
import net.ivanmolto.music.db.SongDao
import net.ivanmolto.music.util.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Song instances.
 */
@Singleton
// @OpenForTesting
class SongRepository @Inject constructor(
    private val appExecutors: AppExecutors,
    private val db: MusicDb,
    private val songDao: SongDao,
    private val tunesService: TunesService
) {

    fun search(query: String): LiveData<Resource<List<Song>>> {
        return object : NetworkBoundResource<List<Song>, SongSearchResponse>(appExecutors) {
            override fun saveCallResult(item: SongSearchResponse) {
                val songIds = item.results.map { it.id }
                val songSearchResult = SongSearchResult(
                    query = query,
                    songIds = songIds,
                    totalCount = item.resultCount
                    // next = item.nextPage
                )
                db.runInTransaction {
                    songDao.insertSongs(item.results)
                    songDao.insert(songSearchResult)
                }
            }

            override fun shouldFetch(data: List<Song>?) = data == null

            override fun loadFromDb(): LiveData<List<Song>> {
                return songDao.search(query).switchMap { searchData ->
                    if (searchData == null) {
                        AbsentLiveData.create()
                    } else {
                        songDao.loadOrdered(searchData.songIds)
                    }
                }
            }

            override fun createCall() = tunesService.searchSongs(query)

            override fun processResponse(response: ApiSuccessResponse<SongSearchResponse>): SongSearchResponse {
                val body = response.body
                return body
            }
        }.asLiveData()
    }

    fun getSong(id: Int): LiveData<Song> = songDao.getSong(id)
}
