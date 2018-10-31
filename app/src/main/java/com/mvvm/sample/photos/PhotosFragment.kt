package com.mvvm.sample.photos

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.sample.R
import com.mvvm.sample.base.BaseFragment
import com.mvvm.sample.data.Photo
import com.mvvm.sample.view.SimpleDividerItemDecorator
import kotlinx.android.synthetic.main.fragment_photos.*

class PhotosFragment : BaseFragment(), PhotoItemView.OnPhotoClickListener {

    companion object {
        const val TAG = "PhotosFragment"
        fun newInstance() = PhotosFragment()
    }

    private val viewModel by lazy { ViewModelProviders.of(this).get(PhotosViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        getPhotos()
    }

    private fun initViewModel() {
        viewModel.onPhotosSuccess.observe(this, onPhotosSuccessObserver)
        viewModel.onPhotosFailure.observe(this, onPhotosFailureObserver)
        viewModel.onNetworkError.observe(this, onNetworkErrorObserver)
    }

    private fun getPhotos() {
        viewModel.getPhotos(getViewContext())
        showProgress()
    }

    private val onPhotosSuccessObserver = Observer<List<Photo>> {
        hideProgress()
        rvPhotos.apply {
            layoutManager = LinearLayoutManager(getViewContext())
            setHasFixedSize(true)
            addItemDecoration(SimpleDividerItemDecorator(getViewContext()))
            adapter = PhotosAdapter(it, this@PhotosFragment)
        }
    }

    private val onPhotosFailureObserver = Observer<Unit> {
        hideProgress()
        showAlert(R.string.error_loading_photos)
    }

    private val onNetworkErrorObserver = Observer<Unit> {
        hideProgress()
        showAlert(R.string.error_network)
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