package com.hendyapp.mblfavorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.hendyapp.mybooklist.databinding.FragmentHomeBinding
import com.hendyapp.mybooklist.detail.DetailActivity
import com.hendyapp.mybooklist.main.BookListAdapter
import com.hendyapp.mybooklist.main.MainActivity
import com.hendyapp.mybooklist.main.MainViewModel
import kotlinx.coroutines.launch

class FavoriteFragment: Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private lateinit var bookAdapter: BookListAdapter

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.editSearch.visibility = View.GONE

        viewModel = (requireActivity() as MainActivity).viewModel

        binding.recyclerBooks.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            bookAdapter = BookListAdapter ({
                val intent = Intent(requireActivity(), DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA, it)
                startActivity(intent)
            }, true)
            adapter = bookAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateBookFavorite.collect { list ->
                    bookAdapter.submitList(list)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getFavoriteBooks()
    }
}