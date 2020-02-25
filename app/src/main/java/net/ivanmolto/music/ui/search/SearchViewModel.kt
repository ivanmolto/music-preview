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

package net.ivanmolto.music.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
// import net.ivanmolto.music.testing.OpenForTesting
import net.ivanmolto.music.repository.SongRepository
import net.ivanmolto.music.util.AbsentLiveData
import net.ivanmolto.music.vo.Resource
import net.ivanmolto.music.vo.Song

import java.util.Locale
import javax.inject.Inject

// @OpenForTesting
class SearchViewModel @Inject constructor(songRepository: SongRepository) : ViewModel() {

    private val _query = MutableLiveData<String>()
    val query: LiveData<String> = _query

    val results: LiveData<Resource<List<Song>>> = _query.switchMap { search ->
        if (search.isBlank()) {
            AbsentLiveData.create()
        } else {
            songRepository.search(search)
        }
    }

    fun setQuery(originalInput: String) {
        val input = originalInput.toLowerCase(Locale.getDefault()).trim()
        if (input == _query.value) {
            return
        }
        _query.value = input
    }

    fun refresh() {
        _query.value?.let {
            _query.value = it
        }
    }
}
