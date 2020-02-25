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

import androidx.databinding.BindingAdapter
import android.view.View
import android.widget.TextView

/**
 * Data Binding adapters
 */
object BindingAdapters {
    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("minimify")
    fun showMin(view: TextView, blurb: String) {
        if (blurb.length > 36) {
            view.text = blurb.take(36) + ".."
        } else {
            view.text = blurb
        }
    }

    @JvmStatic
    @BindingAdapter("floatToString")
    fun stringify(view: TextView, price: Float) {
        view.text = "$" + price.toString()
    }

    @JvmStatic
    @BindingAdapter("millisToTime")
    fun timify(view: TextView, duration: Int) {

        val minutes = (duration / 1000) / 60
        val seconds = (duration / 1000) % 60

        view.text = minutes.toString() + ":" + seconds.toString()
    }

    @JvmStatic
    @BindingAdapter("dateYear")
    fun yearify(view: TextView, blurb: String) {

        view.text = blurb.take(4)
    }
}
