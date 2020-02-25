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

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.ivanmolto.music.AppExecutors
import net.ivanmolto.music.R
import net.ivanmolto.music.binding.FragmentDataBindingComponent
import net.ivanmolto.music.databinding.SearchFragmentBinding
import net.ivanmolto.music.di.Injectable
import net.ivanmolto.music.ui.common.SongListAdapter
import net.ivanmolto.music.ui.common.RetryCallback
import net.ivanmolto.music.util.autoCleared
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    var binding by autoCleared<SearchFragmentBinding>()
    var adapter by autoCleared<SongListAdapter>()
    val searchViewModel: SearchViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        initRecyclerView()
        val rvAdapter = SongListAdapter(
            dataBindingComponent = dataBindingComponent,
            appExecutors = appExecutors
        ) { song ->
            findNavController().navigate(
                SearchFragmentDirections.showSong(song.id, song.music!!)
            )
        }
        binding.query = searchViewModel.query
        binding.songList.adapter = rvAdapter
        adapter = rvAdapter
        initSearchInputListener()
        binding.callback = object : RetryCallback {
            override fun retry() {
                searchViewModel.refresh()
            }
        }
    }

    private fun initSearchInputListener() {
        // binding.input.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
        binding.input.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                doSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun doSearch(text: String) {
        val query = text
        searchViewModel.setQuery(query)
    }

    private fun initRecyclerView() {
        binding.songList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == adapter.itemCount - 1) {
                }
            }
        })
        binding.searchResult = searchViewModel.results
        searchViewModel.results.observe(viewLifecycleOwner, Observer { result ->
            adapter.submitList(result?.data)
        })
    }
}
