package com.saheralsous.android.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.saheralsous.android.R

class LoadStateViewHolder(
    private val binding: View,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding){

    private val retryButton = binding.findViewById(R.id.retry_button) as Button
    private val progressBar = binding.findViewById(R.id.progress_bar) as ProgressBar
    private val errorMsg = binding.findViewById(R.id.error_msg) as TextView

    init {
        retryButton.setOnClickListener {
            retry.invoke()
        }
    }

    fun onBind(loadState: LoadState){
        when (loadState){
            is LoadState.Loading -> {
                retryButton.isVisible = false
                errorMsg.isVisible = false
                progressBar.isVisible = true
            }
            is LoadState.NotLoading -> {
                retryButton.isVisible = false
                errorMsg.isVisible = false
                progressBar.isVisible = false
            }
            is LoadState.Error -> {
                errorMsg.text = loadState.error.message
                retryButton.isVisible = true
                errorMsg.isVisible = true
                progressBar.isVisible = false
            }
        }
    }

    companion object{
        fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder{
            val view = LayoutInflater.from((parent.context))
                .inflate(R.layout.load_state_footer_view_item, parent, false)
            return LoadStateViewHolder(view, retry)
        }
    }
}