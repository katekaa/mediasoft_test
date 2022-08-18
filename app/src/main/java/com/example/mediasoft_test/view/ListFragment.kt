package com.example.mediasoft_test.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mediasoft_test.adapter.ListAdapter
import com.example.mediasoft_test.adapter.ListLoadStateAdapter
import com.example.mediasoft_test.databinding.ListFragmentBinding
import com.example.mediasoft_test.model.Character
import com.example.mediasoft_test.repository.CharacterRepository
import com.example.mediasoft_test.viewmodel.CharacterViewModel
import com.example.mediasoft_test.viewmodel.CharacterViewModelFactory
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.coroutines.ExperimentalCoroutinesApi


class ListFragment : Fragment() {

    private lateinit var binding: ListFragmentBinding

    @ExperimentalCoroutinesApi
    val viewModel: CharacterViewModel by viewModels { CharacterViewModelFactory(CharacterRepository()) }
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: ListAdapter
    private lateinit var footerAdapter: ListLoadStateAdapter
    private val disposable = CompositeDisposable()


    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater)
        recycler = binding.recyclerView
        adapter = ListAdapter { binding.deleteButton.isVisible = it }
        footerAdapter = ListLoadStateAdapter()

        val adapterWithState = adapter.withLoadStateFooter(footerAdapter)
        val refreshLayout = binding.refreshLayout

        refreshLayout.setOnRefreshListener {
            adapter.refresh()
            refreshLayout.isRefreshing = false
        }

        binding.deleteButton.setOnClickListener {
            deleteCharacters(adapter.selectedCharacters)
            adapter.selectedPositions.forEach{
                adapter.notifyItemChanged(it)
            }
            adapter.selectedCharacters.clear()
            adapter.selectionMode = false
            adapter.selectedPositions.clear()
            binding.deleteButton.isVisible = false
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
        disposable.add(viewModel.callApi(null, null).subscribe() { pagingData ->
            adapter.submitData(lifecycle, pagingData.map { it.toCharacter() })
            }
        )

        recycler.scrollToPosition(0)

        return binding.root

    }

    @ExperimentalCoroutinesApi
    @OptIn(androidx.paging.ExperimentalPagingApi::class)
    private fun deleteCharacters(chars: MutableList<Character>) {
        val charsToDelete = chars.map { it.toCharacterRoomEntity() }.toList()

        disposable.dispose()
        disposable.add(viewModel.callApi(charsToDelete, binding.root).subscribe() { pagingData ->
            adapter.submitData(lifecycle, pagingData.map { it.toCharacter() })
            }
        )
        adapter.selectedCharacters.forEach { it.selected = false }

    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}
