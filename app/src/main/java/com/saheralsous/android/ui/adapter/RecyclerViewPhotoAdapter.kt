package com.saheralsous.android.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.saheralsous.android.R
import com.saheralsous.android.database.remote.model.PagingData
import com.saheralsous.android.utils.getProgressDrawable
import com.saheralsous.android.utils.loadImage

class RecyclerViewPhotoAdapter() :
    PagingDataAdapter<PagingData.GalleryItem, PhotoHolder>(
        diffCallback = DiffCallback
    ) {
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.photo_item_view,parent,false)
        return PhotoHolder(view)
    }
}

class PhotoHolder(view: View):
    RecyclerView.ViewHolder(view), View.OnClickListener {
    /**
     * changing the input to view the image, after downloading it with glide
     *
    private var idTextView: TextView = view.findViewById(R.id._id)
    private var ownerTextView: TextView = view.findViewById(R.id._owner)
    private var titleTextView: TextView = view.findViewById(R.id._title)
    private var urlTextView: TextView = view.findViewById(R.id._url)
     */
    private val imageButtom: ImageButton = view.findViewById(R.id.ImageButton)
    private lateinit var galleryItem: PagingData.GalleryItem
    /**
     * passing url to the Web View
     */

    @SuppressLint("ResourceType")
    fun bind(galleryItem : PagingData.GalleryItem){
        this.galleryItem = galleryItem

        /*
        idTextView.text = galleryItem.id
        ownerTextView.text = galleryItem.owner
        titleTextView.text = galleryItem.title
        urlTextView.text = galleryItem.url
         */
//        galleryItem.url.let { url ->
//            Glide.with(itemView)
//                .load(url)
//                .override(350,350)
//                .into(imageButtom)
//        }
        /**
         * loading image using the ImageView Extension
         * getting the context from the image_view context.
         */
        imageButtom.loadImage(galleryItem.url, getProgressDrawable(imageButtom.context))
    }

    init {
        imageButtom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val bundle = bundleOf("url" to galleryItem.photoPageUri.toString() )
        Navigation.findNavController(v!!).navigate(
            R.id.action_photoGalleryFragment_to_photoPageFragment,
            bundle)
    }
}




object DiffCallback : DiffUtil.ItemCallback<PagingData.GalleryItem>() {

    override fun areItemsTheSame(oldItem: PagingData.GalleryItem, newItem: PagingData.GalleryItem): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PagingData.GalleryItem, newItem: PagingData.GalleryItem): Boolean {
        return oldItem == newItem
    }
}