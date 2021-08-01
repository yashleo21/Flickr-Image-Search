package com.yash2108.imagelookup.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.yash2108.imagelookup.R
import com.yash2108.imagelookup.databinding.FragmentFavoriteBinding
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.openissuesreader.adapters.FavoriteAdapter
import com.yash2108.openissuesreader.models.ResultUI
import com.yash2108.openissuesreader.viewmodels.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment: Fragment(), FavoriteAdapter.Callback {

    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<HomeViewModel>()

    @Inject
    lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObservers()
        fetchData()
    }

    private fun initAdapter() {
        adapter.callback = this
        binding.rvItems.adapter = adapter
    }

    private fun initObservers() {
        viewModel.favoriteDataLiveData.observe(viewLifecycleOwner, Observer {
            onDataStateLoaded(it)
        })
    }

    private fun onDataStateLoaded(state: ResultUI<List<FlickrDataObject>>) {
        when (state) {
            is ResultUI.Loading -> {
                binding.pb.visibility = View.VISIBLE
            }

            is ResultUI.Error -> {
                binding.pb.visibility = View.GONE
            }

            is ResultUI.Success -> {
                binding.pb.visibility = View.GONE
                updateAdapter(state.data)
            }
        }
    }

    private fun updateAdapter(data: List<FlickrDataObject>) {
        adapter.submitList(data.toList())
    }


    private fun fetchData() {
        viewModel.getFavorites()
    }

    override fun onItemClicked(data: FlickrDataObject, position: Int, transitionView: View) {
        viewModel.currentItem = data

        val fragment = DetailFragment()

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.setCustomAnimations(
            R.anim.slide_in_right, R.anim.slide_out_right,
            R.anim.slide_in_right, R.anim.slide_out_right)

        transaction
            ?.add(R.id.fragment_containing_view, fragment, "detailFragment")
            ?.addToBackStack("detail")
            ?.setReorderingAllowed(true)
            ?.commit()
    }

    override fun onDestroyView() {
        viewModel.favoriteDataLiveData.removeObservers(this)
        viewModel.favoriteDataMutableLiveData = MutableLiveData()
        _binding = null
        super.onDestroyView()
    }
}