package com.yash2108.imagelookup.ui.fragment

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.yash2108.imagelookup.R
import com.yash2108.imagelookup.databinding.FragmentHomeBinding
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.imagelookup.utils.Utils
import com.yash2108.openissuesreader.adapters.HomeAdapter
import com.yash2108.openissuesreader.models.ResultUI
import com.yash2108.openissuesreader.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment: Fragment(), HomeAdapter.Callback {

    private val TAG = HomeFragment::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<HomeViewModel>()

    @Inject
    lateinit var adapter: HomeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initListeners()
        initObservers()
        fetchData()
        Log.d(TAG, "onview created called")
    }

    private fun initListeners() {
        binding.rvItems.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = adapter.itemCount
                val lastVisibleItem = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                if (!viewModel.isPaginationCallInProgress && totalItemCount > 0 && lastVisibleItem == (totalItemCount - 1)) {
                    paginateFeed()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })

        binding.searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.trim()?.isNotBlank() == true) {
                    if (Utils.isConnected(context)) {
                        viewModel.adapterList.clear()
                        adapter.submitList(emptyList())
                        viewModel.page = 1L
                        viewModel.query = query ?: ""
                        fetchData()
                    } else {
                        Toast.makeText(context, "Not connected to internet!", Toast.LENGTH_SHORT).show()
                    }
                }
                binding.searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        binding.fabFavorite.setOnClickListener {
            showFavoriteFragment()
        }
    }

    private fun paginateFeed() {
        if (Utils.isConnected(context)) {
            viewModel.page = viewModel.page + 1
            fetchData()
        }
    }

    private fun initAdapter() {
        adapter.callback = this
        binding.rvItems.adapter = adapter
    }

    private fun initObservers() {
        viewModel.homeDataObjectDataLiveData.observe(viewLifecycleOwner, Observer {
            onDataStateLoaded(it)
        })
    }

    private fun onDataStateLoaded(state: ResultUI<List<FlickrDataObject>>) {
        when (state) {
            is ResultUI.Loading -> {
                viewModel.isPaginationCallInProgress = true
                binding.pb.visibility = View.VISIBLE
                Log.d(TAG, "LOADING TRIGGERED")
            }

            is ResultUI.Error -> {
                viewModel.isPaginationCallInProgress = false
                binding.pb.visibility = View.GONE
                Log.d(TAG, "Error triggered")
            }

            is ResultUI.Success -> {
                Log.d(TAG, "Success")
                viewModel.isPaginationCallInProgress = false
                binding.pb.visibility = View.GONE
                updateAdapter(state.data)
            }
        }
    }

    private fun updateAdapter(data: List<FlickrDataObject>) {
        Log.d(TAG, "Update adapter called")
        viewModel.adapterList.addAll(data)

        adapter.submitList(viewModel.adapterList.toList())
    }

    private fun fetchData() {
        viewModel.getPhotosList(viewModel.query, viewModel.page, viewModel.pageSize)
    }

    private fun showFavoriteFragment() {
        val fragment = FavoriteFragment()
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
            R.anim.slide_in_right, R.anim.slide_out_right)

        transaction
            ?.add(R.id.fragment_containing_view, fragment, "favoriteFragment")
            ?.addToBackStack("favorite")
            ?.setReorderingAllowed(true)
            ?.commit()
    }

    override fun onItemClicked(data: FlickrDataObject, position: Int, transitionView: View) {
        viewModel.currentItem = data

        val fragment = DetailFragment()

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right,
            R.anim.slide_in_right, R.anim.slide_out_right)

        transaction
            ?.add(R.id.fragment_containing_view, fragment, "detailFragment")
            ?.addToBackStack("detail")
            ?.setReorderingAllowed(true)
            ?.commit()
    }

    override fun setFavoriteState(data: FlickrDataObject, isFavorite: Boolean, position: Int) {
        viewModel.adapterList?.elementAtOrNull(position)?.isFavorite = isFavorite
        viewModel?.setFavoriteState(data)
    }

    override fun onDestroyView() {
        viewModel.homeDataObjectDataLiveData.removeObservers(this)
        viewModel.homeDataMutableLiveData = MutableLiveData()
        viewModel.adapterList.clear()
        _binding = null
        super.onDestroyView()
    }
}