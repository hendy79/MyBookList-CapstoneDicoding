package com.hendyapp.mybooklist.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.hendyapp.mblcore.data.Resource
import com.hendyapp.mybooklist.databinding.FragmentHomeBinding
import com.hendyapp.mybooklist.detail.DetailActivity
import com.hendyapp.mybooklist.main.BookListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var bookAdapter: BookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerBooks.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            bookAdapter = BookListAdapter ({
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                startActivity(intent)
            })
            adapter = bookAdapter
        }

        binding.editSearch.addTextChangedListener {
            viewModel.setTextSearch(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateSearch.debounce(500).collect {
                    if(!it.isNullOrEmpty()) {
                        if(binding.editSearch.text.isNullOrEmpty())
                            binding.editSearch.setText(it)
                        viewModel.searchBooks(it)
                    } else {
                        bookAdapter.submitList(null)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateBookResource.collect {
                    when(it) {
                        is Resource.Success -> {
                            bookAdapter.submitList(it.data.orEmpty())
                        }
                        is Resource.Error -> {
                            Toast.makeText(requireContext(), it.message ?: "", Toast.LENGTH_SHORT).show()
                        }
                        else -> {

                        }
                    }
                }
            }
        }
    }
}