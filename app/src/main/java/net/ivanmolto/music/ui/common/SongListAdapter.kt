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

package net.ivanmolto.music.ui.common

import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import net.ivanmolto.music.AppExecutors
import net.ivanmolto.music.R
import net.ivanmolto.music.databinding.SongItemBinding
import net.ivanmolto.music.vo.Song

/**
 * The RecyclerView adapter for the [Song] class.
 */
class SongListAdapter(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val songClickCallback: ((Song) -> Unit)?
) : DataBoundListAdapter<Song, SongItemBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<Song>() {
        override fun areItemsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Song, newItem: Song): Boolean {
            return oldItem == newItem
        }
    }
) {

    override fun createBinding(parent: ViewGroup): SongItemBinding {
        val binding = DataBindingUtil.inflate<SongItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.song_item,
            parent,
            false,
            dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.song?.let {
                songClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: SongItemBinding, item: Song) {
        binding.song = item
    }
}
