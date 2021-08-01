package com.yash2108.openissuesreader.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestOptions
import com.yash2108.imagelookup.R
import com.yash2108.imagelookup.databinding.ListItemBinding
import com.yash2108.imagelookup.models.FlickrDataObject
import com.yash2108.imagelookup.utils.setAspectRatio
import javax.inject.Inject

class HomeAdapter @Inject constructor() :
    ListAdapter<FlickrDataObject, HomeAdapter.ItemViewHolder>(HomeDiffUtil()) {

    private val TAG = HomeAdapter::class.simpleName
    lateinit var callback: Callback

    private val requestOptions = RequestOptions().placeholder(R.color.placeholder_color)
        .fallback(R.color.placeholder_color)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(getItem(position), position)
    }

    inner class ItemViewHolder(val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setAspectRatio(dividedWidth = 1f, aspectRatio = 1f, totalMargin = 40)
            attachListeners()
        }

        private fun attachListeners() {
            binding.root.setOnClickListener {
                if (adapterPosition < 0) return@setOnClickListener
                val position = adapterPosition

                callback.onItemClicked(getItem(position), position, binding.root)
            }

            binding.cvFavorite.setOnClickListener {
                if (adapterPosition < 0) return@setOnClickListener
                val position = adapterPosition
                getItem(position)?.isFavorite?.let {
                    if (it) {
                        getItem(position)?.isFavorite = false
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_unfilled)
                        callback.setFavoriteState(getItem(position), false, position)
                    } else {
                        getItem(position)?.isFavorite = true
                        binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
                        callback.setFavoriteState(getItem(position), true, position)
                    }
                }
            }
        }


        fun bindView(data: FlickrDataObject, position: Int) {
            val imageUrl =
                "https://farm${data.farm}.staticflickr.com/${data.server}/${data.id}_${data.secret}_m.jpg"

            Glide.with(binding.ivImage)
                .load(imageUrl)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(binding.ivImage)

            if (data.isFavorite) {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_filled)
            } else {
                binding.ivFavorite.setImageResource(R.drawable.ic_favorite_unfilled)
            }
        }
    }

    class HomeDiffUtil : DiffUtil.ItemCallback<FlickrDataObject>() {
        override fun areItemsTheSame(
            oldItem: FlickrDataObject,
            newItem: FlickrDataObject
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: FlickrDataObject,
            newItem: FlickrDataObject
        ): Boolean {
            return oldItem == newItem
        }
    }

    interface Callback {
        fun onItemClicked(data: FlickrDataObject, position: Int, transitionView: View)
        fun setFavoriteState(data: FlickrDataObject, isFavorite: Boolean, position: Int)
    }
}