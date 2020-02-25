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

package net.ivanmolto.music.ui.song

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
// import net.ivanmoto.music.testing.OpenForTesting
import net.ivanmolto.music.repository.SongRepository
import net.ivanmolto.music.ui.song.mediaplayer.MediaPlayerImpl
import net.ivanmolto.music.util.AbsentLiveData
import net.ivanmolto.music.vo.Song
import javax.inject.Inject

// @OpenForTesting
class SongViewModel @Inject constructor(songRepository: SongRepository) : ViewModel() {

    private val mediaPlayer = MediaPlayerImpl()

    fun getMediaPlayer() = mediaPlayer

    fun play(url: String) = mediaPlayer.play(url)

    fun release() = mediaPlayer.releasePlayer()

    private val _urlMusic = MutableLiveData<String?>()
    val urlMusic: LiveData<String?>
        get() = _urlMusic

    fun setUrlMusic(urlMusic: String?) {
        if (_urlMusic.value != urlMusic) {
            _urlMusic.value = urlMusic
        }
    }

    private val _id = MutableLiveData<Int?>()
    val id: LiveData<Int?>
        get() = _id

    val song: LiveData<Song> = _id.switchMap { id ->
        if (id == null) {
            AbsentLiveData.create()
        } else {
            songRepository.getSong(id)
        }
    }

    fun setId(id: Int?) {
        if (_id.value != id) {
            _id.value = id
        }
    }

    fun retry() {

        _id.value?.let {
            _id.value = it
        }
    }
}
