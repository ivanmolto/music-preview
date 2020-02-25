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

}
