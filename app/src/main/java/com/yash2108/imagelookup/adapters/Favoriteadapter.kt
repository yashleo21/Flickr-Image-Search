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

class Favoriteadapter @Inject constructor() : ListAdapter<FlickrDataObject, Favoriteadapter.ItemViewHolder>(HomeDiffUtil()) {

    private val TAG = Favoriteadapter::class.simpleName
    lateinit var callback: Callback

    private val requestOptions = RequestOptions().placeholder(R.color.placeholder_color)
        .fallback(R.color.placeholder_color)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindView(getItem(position), position)
    }

    inner class ItemViewHolder(val binding: ListItemBinding): RecyclerView.ViewHolder(binding.root){

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
        }


        fun bindView(data: FlickrDataObject, position: Int) {
            val imageUrl = "https://farm${data.farm}.staticflickr.com/${data.server}/${data.id}_${data.secret}_m.jpg"

            Glide.with(binding.ivImage)
                .load(imageUrl)
                .apply(requestOptions)
                .transition(withCrossFade())
                .into(binding.ivImage)

            binding.ivFavorite.visibility = View.GONE
        }
    }

    class HomeDiffUtil(): DiffUtil.ItemCallback<FlickrDataObject>() {
        override fun areItemsTheSame(oldItem: FlickrDataObject, newItem: FlickrDataObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FlickrDataObject, newItem: FlickrDataObject): Boolean {
            return oldItem == newItem
        }
    }

    interface Callback {
        fun onItemClicked(data: FlickrDataObject, position: Int, transitionView: View)
    }
}