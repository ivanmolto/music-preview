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

package net.ivanmolto.music.vo

import androidx.room.Entity
import androidx.room.Index
import com.google.gson.annotations.SerializedName

@Entity(
    indices = [
        Index("duration"),
        Index("genre"),
        Index("price")],
    primaryKeys = ["id"]
)

data class Song(
    @field:SerializedName("trackId")
    val id: Int,
    @field:SerializedName("trackName")
    val track: String?,
    @field:SerializedName("artistName")
    val artist: String,
    @field:SerializedName("collectionName")
    val album: String?,
    @field:SerializedName("previewUrl")
    val music: String?,
    @field:SerializedName("releaseDate")
    val release: String,
    @field:SerializedName("trackTimeMillis")
    val duration: Int?,
    @field:SerializedName("primaryGenreName")
    val genre: String,
    @field:SerializedName("trackPrice")
    val price: Float?,
    @field:SerializedName("currency")
    val currency: String,
    @field:SerializedName("artworkUrl60")
    val thumbnail: String?,
    @field:SerializedName("artworkUrl100")
    val cover: String?
)
