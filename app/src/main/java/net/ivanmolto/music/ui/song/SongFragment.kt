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

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.ui.PlayerView
import net.ivanmolto.music.AppExecutors
import net.ivanmolto.music.R
import net.ivanmolto.music.binding.FragmentDataBindingComponent
import net.ivanmolto.music.databinding.SongFragmentBinding
import net.ivanmolto.music.di.Injectable
import net.ivanmolto.music.ui.common.RetryCallback
import net.ivanmolto.music.util.autoCleared
import javax.inject.Inject

/**
 * The UI for displaying the song info
 */
class SongFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var binding by autoCleared<SongFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private val songViewModel: SongViewModel by viewModels {
        viewModelFactory
    }

    lateinit var exoPlayer: ExoPlayer
    lateinit var playerView: PlayerView
    private val params by navArgs<SongFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<SongFragmentBinding>(
            inflater,
            R.layout.song_fragment,
            container,
            false,
            dataBindingComponent
        )

        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                songViewModel.retry()
            }
        }


        binding = dataBinding
        sharedElementReturnTransition = TransitionInflater.from(context).inflateTransition(R.transition.move)

        setHasOptionsMenu(true)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        songViewModel.setId(params.id)
        songViewModel.setUrlMusic(params.urlMusic)
        binding.args = params
        binding.song = songViewModel.song
        binding.lifecycleOwner = viewLifecycleOwner

        exoPlayer = songViewModel.getMediaPlayer().getPlayer(context!!)
        playerView = binding.mplayer
        playerView.player = exoPlayer
        songViewModel.play(params.urlMusic)
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            songViewModel.release()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            songViewModel.release()
        }
    }

    // Share

    /*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_music, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                createShareIntent()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = songViewModel.song.value.let { song ->
            if (song == null) {
                ""
            } else {
                getString(R.string.share_text_song, song.track)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(context!!)
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }

     */
}
