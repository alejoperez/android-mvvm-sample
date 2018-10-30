package com.mvvm.sample.photos

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

class PhotosFragment : BaseFragment(), IPhotosContract.View, PhotoItemView.OnPhotoClickListener {

    companion object {
        const val TAG = "PhotosFragment"
        fun newInstance() = PhotosFragment()
    }

    private val presenter by lazy { PhotosPresenter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getPhotos()
    }

    override fun onPhotosSuccess(photos: List<Photo>?) {
        rvPhotos.apply {
            layoutManager = LinearLayoutManager(getViewContext())
            setHasFixedSize(true)
            addItemDecoration(SimpleDividerItemDecorator(getViewContext()))
            adapter = PhotosAdapter(photos, this@PhotosFragment)
        }
    }

    override fun onPhotosFailure() {
        showAlert(R.string.error_loading_photos)
    }

    override fun onPhotoClicked(photo: Photo?) {
        PhotoDetailDialogFragment.newInstance(photo?.url).show(fragmentManager, PhotoDetailDialogFragment.TAG)
    }

    override fun showProgress() {
        rvPhotos.visibility = View.INVISIBLE
        progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        rvPhotos.visibility = View.VISIBLE
        progress.visibility = View.INVISIBLE
    }

}