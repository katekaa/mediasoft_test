package com.example.mediasoft_test.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediasoft_test.adapter.ListAdapter
import com.example.mediasoft_test.adapter.ListLoadStateAdapter
import com.example.mediasoft_test.databinding.ListFragmentBinding
import com.example.mediasoft_test.repository.CharacterRepository
import com.example.mediasoft_test.viewmodel.CharacterViewModel
import com.example.mediasoft_test.viewmodel.CharacterViewModelFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi


class ListFragment: Fragment() {

    private lateinit var binding: ListFragmentBinding
    @ExperimentalCoroutinesApi
    val viewModel: CharacterViewModel by viewModels { CharacterViewModelFactory(CharacterRepository()) }
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var footerAdapter: ListLoadStateAdapter
    private val disposable = CompositeDisposable()

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater)

        recycler = binding.recyclerView
        adapter = ListAdapter()
        footerAdapter = ListLoadStateAdapter()
        val adapterWithState = adapter.withLoadStateFooter(footerAdapter)
        val refreshLayout = binding.refreshLayout
        refreshLayout.setOnRefreshListener {
            adapter.refresh()
        }

        adapter.addLoadStateListener {

            when (it.refresh) {
                is LoadState.NotLoading -> {
                    refreshLayout.isRefreshing = false
                }
                is LoadState.Loading -> {
                }
                is LoadState.Error -> {
                    refreshLayout.isRefreshing = false
                }
            }
        }

        recycler.layoutManager = LinearLayoutManager(this.context)
        recycler.adapter = adapterWithState
        disposable.add(viewModel.callApi().subscribe() {
                adapter.submitData(lifecycle, it)
            Log.d("aboba", "it in fragment ${it}")
            })


        return binding.root

    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
