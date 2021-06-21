package com.saheralsous.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.saheralsous.android.R
import com.saheralsous.android.database.remote.model.PagingData

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
    RecyclerView.ViewHolder(view){

    private var idTextView: TextView = view.findViewById(R.id._id)
    private var ownerTextView: TextView = view.findViewById(R.id._owner)
    private var titleTextView: TextView = view.findViewById(R.id._title)
    private var urlTextView: TextView = view.findViewById(R.id._url)


    fun bind(galleryItem : PagingData.GalleryItem){
        idTextView.text = galleryItem.id
        ownerTextView.text = galleryItem.owner
        titleTextView.text = galleryItem.title
        urlTextView.text = galleryItem.url
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