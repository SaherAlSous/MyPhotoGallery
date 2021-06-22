package com.saheralsous.android

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.saheralsous.android.database.repository.GalleryFlowRepositoryImpl
import com.saheralsous.android.ui.adapter.PhotoPaging
import com.saheralsous.android.ui.adapter.RecyclerViewPhotoAdapter
import com.saheralsous.android.ui.adapter.TaskLoadStateAdapter
import com.saheralsous.android.ui.viewmodel.PhotoGalleryViewModel
import com.saheralsous.android.utils.MyApplication
import com.saheralsous.android.utils.ViewModelProviderFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class PhotoGalleryFragment : Fragment() {

    private lateinit var viewModel : PhotoGalleryViewModel
    private lateinit var repository: GalleryFlowRepositoryImpl
    private lateinit var pagingAdapter : PhotoPaging
    private lateinit var PhotoRecyclerView : RecyclerView
    private lateinit var RecyclerViewPhotoAdapter : RecyclerViewPhotoAdapter
    private lateinit var progressBar : ProgressBar


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)
        /**
         * PagingDataAdapter
         */

        RecyclerViewPhotoAdapter = RecyclerViewPhotoAdapter()

        /**
         * Recycler View
          */
        PhotoRecyclerView =
            view.findViewById(R.id.recyclerview_main) as RecyclerView
        PhotoRecyclerView.layoutManager = GridLayoutManager(context, 3)

        //linking adapter with recyclerview
        PhotoRecyclerView.adapter = RecyclerViewPhotoAdapter

        /**
         * Adapter, Repository and viewmodel
         */
        pagingAdapter = PhotoPaging(
            (requireActivity().application as MyApplication).networkApi
        )

        repository = GalleryFlowRepositoryImpl(pagingAdapter)

        viewModel = ViewModelProvider(this, ViewModelProviderFactory(
            PhotoGalleryViewModel::class
        ){
            PhotoGalleryViewModel(repository)
        }).get(PhotoGalleryViewModel::class.java)

        /**
         * adding load state with progress bar
         */
        progressBar = view.findViewById(R.id.progressBar)
        RecyclerViewPhotoAdapter.addLoadStateListener { loadState ->

            //show Progress bar when the load state is loading
            progressBar.isVisible =
                loadState.source.refresh is LoadState.Loading

            //LoadState for the error and show the message on the UI
            val errorState = loadState.source.refresh as? LoadState.Error //PagingSource loadState
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.source.append as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error //Remote Mediator LoadState
                ?: loadState.append as? LoadState.Error
                ?:loadState.prepend as? LoadState.Error

            errorState?.let {
                showErrorSnackBar(it.error.message.toString())
            }
        }

        /**
         * Seting up the load state adapter: retry, Load & Error
         */
        PhotoRecyclerView.adapter=
        RecyclerViewPhotoAdapter.withLoadStateHeaderAndFooter(
            header = TaskLoadStateAdapter{ RecyclerViewPhotoAdapter.retry() },
            footer = TaskLoadStateAdapter{ RecyclerViewPhotoAdapter.retry() }
        )



        //observing data
        observers()
        // Inflate the layout for this fragment
        return view
    }

    private fun observers() {
        lifecycleScope.launch {
            viewModel.getGalleryPaging()
                .collectLatest {
                    RecyclerViewPhotoAdapter.submitData(lifecycle,it)
                }
        }
    }

    private fun showErrorSnackBar(msg: String){
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_INDEFINITE).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_200))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.purple_700))
            setActionTextColor(ContextCompat.getColor(requireContext(), R.color.purple_500))
            setAction("Close") {
                dismiss()
            }
            anchorView = progressBar
        }.show()
    }

}