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

package net.ivanmolto.music.binding

import android.graphics.drawable.Drawable
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.databinding.BindingAdapter
// import net.ivanmolto.music.search.testing.OpenForTesting
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment
 */
// @OpenForTesting
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(fragment).load(url).listener(listener).into(imageView)
    }
}
