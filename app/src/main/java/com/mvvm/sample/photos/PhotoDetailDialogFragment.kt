package com.mvvm.sample.photos

import android.annotation.SuppressLint
import com.mvvm.sample.base.BaseDialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import com.mvvm.sample.BR
import com.mvvm.sample.R
import com.mvvm.sample.data.room.Photo
import com.mvvm.sample.databinding.DialogPhotoDetailBinding

class PhotoDetailDialogFragment : BaseDialogFragment<PhotoDetailViewModel, DialogPhotoDetailBinding>() {

    override fun getLayoutId(): Int = R.layout.dialog_photo_detail
    override fun getViewModelClass(): Class<PhotoDetailViewModel> = PhotoDetailViewModel::class.java
    override fun getVariablesToBind(): Map<Int, Any> = mapOf(
            BR.photo to arguments?.getParcelable(PHOTO) as Photo
    )

    companion object {
        const val TAG = "PhotoDetailDialogFragment"
        private const val PHOTO = "PhotoDetailDialogFragment.PHOTO"

        fun newInstance(photo: Photo) = PhotoDetailDialogFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PHOTO, photo)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        return AlertDialog.Builder(activity).apply {
            setView(dataBinding.root).setPositiveButton(R.string.accept) { _, _ -> dialog.cancel() }
        }.create()
    }

}