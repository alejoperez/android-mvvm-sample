package com.mvvm.sample.photos

import android.arch.lifecycle.Observer
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseFragment
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.databinding.FragmentPhotosBinding
import com.mvvm.sample.livedata.DataResource
import com.mvvm.sample.livedata.Status
import com.mvvm.sample.view.SimpleDividerItemDecorator

class PhotosFragment : BaseFragment<PhotosViewModel,FragmentPhotosBinding>(), PhotoItemView.OnPhotoClickListener {

    companion object {
        const val TAG = "PhotosFragment"
        fun newInstance() = PhotosFragment()
    }

    override fun getLayoutId(): Int = R.layout.fragment_photos

    override fun getViewModelClass(): Class<PhotosViewModel> = PhotosViewModel::class.java

    override fun initViewModel() {
        super.initViewModel()
        viewModel.photos.observe(this, onPhotosResponseObserver)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        super.initView(inflater, container)
        viewModel.getPhotos()
        showProgress()
    }

    private val onPhotosResponseObserver = Observer<DataResource<List<Photo>>> {
        if (it != null) {
            onPhotosResponse(it)
        } else {
            onPhotosFailure()
        }
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
        dataBinding.rvPhotos.apply {
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
        dataBinding.rvPhotos.visibility = View.INVISIBLE
        dataBinding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        dataBinding.rvPhotos.visibility = View.VISIBLE
        dataBinding.progress.visibility = View.INVISIBLE
    }

}