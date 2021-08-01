package com.yash2108.imagelookup.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.yash2108.imagelookup.R
import com.yash2108.imagelookup.databinding.FragmentDetailBinding
import com.yash2108.openissuesreader.viewmodels.HomeViewModel

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<HomeViewModel>()

    private val requestOptions = RequestOptions()
        .skipMemoryCache(true)
        .diskCacheStrategy(DiskCacheStrategy.NONE)
        .override(1600, 1600)
        .placeholder(R.color.placeholder_color)
        .fallback(R.color.placeholder_color)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.currentItem?.let { data ->
            val imageUrl =
                "https://farm${data.farm}.staticflickr.com/${data.server}/${data.id}_${data.secret}_m.jpg"
            Glide.with(binding.ivImage)
                .load(imageUrl)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(binding.ivImage)
        }
    }
}