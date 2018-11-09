package com.mvvm.sample.photos

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseFragment
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Status
import com.mvvm.sample.view.SimpleDividerItemDecorator
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : BaseFragment(), PhotoItemView.OnPhotoClickListener {

    companion object {
        const val TAG = "PhotosFragment"
        fun newInstance() = PhotosFragment()
    }

    private val viewModel by lazy { obtainViewModel(PhotosViewModel::class.java) }

    private val onPhotosResponseObserver = Observer<DataResource<List<Photo>>> {
        if (it != null) {
            onPhotosResponse(it)
        } else {
            onPhotosFailure()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getPhotos()
    }

    private fun initViewModel() {
        viewModel.photos.observe(this, onPhotosResponseObserver)
    }

    private fun getPhotos() {
        viewModel.getPhotos()
        showProgress()
    }

    private fun onPhotosResponse(response: DataResource<List<Photo>>) {
        hideProgress()
        when(response.status) {
            Status.SUCCESS -> onPhotosSuccess(response.data)
            Status.FAILURE -> onPhotosFailure()
            Status.NETWORK_ERROR -> onNetworkError()
        }
    }

    private fun onPhotosSuccess(photos: List<Photo>?) {
        rvPhotos.apply {
            layoutManager = LinearLayoutManager(getViewContext())
            setHasFixedSize(true)
            addItemDecoration(SimpleDividerItemDecorator(getViewContext()))
            adapter = PhotosAdapter(photos, this@PhotosFragment)
        }
    }

    private fun onPhotosFailure() {
        showAlert(R.string.error_loading_photos)
    }

    override fun onPhotoClicked(photo: Photo?) {
        PhotoDetailDialogFragment.newInstance(photo?.url).show(fragmentManager, PhotoDetailDialogFragment.TAG)
    }

    private fun showProgress() {
        rvPhotos.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        rvPhotos.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

}