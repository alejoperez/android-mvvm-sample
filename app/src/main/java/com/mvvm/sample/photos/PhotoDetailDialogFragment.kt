package com.mvvm.sample.photos

import android.annotation.SuppressLint
import com.mvvm.sample.base.BaseDialogFragment
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.mvvm.sample.R

class PhotoDetailDialogFragment : BaseDialogFragment() {

    companion object {
        const val TAG = "PhotoDetailDialogFragment"
        private const val PHOTO_URL = "PhotoDetailDialogFragment.PHOTO_URL"

        fun newInstance(photoUrl: String?) = PhotoDetailDialogFragment().apply {
            arguments = Bundle().apply {
                putString(PHOTO_URL, photoUrl)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = LayoutInflater.from(getViewContext()).inflate(R.layout.dialog_photo_detail, null)

        val photoUrl = arguments?.getString(PHOTO_URL, "")

        Glide.with(this).load(photoUrl).into(view.findViewById(R.id.ivPhotoDetail))

        return AlertDialog.Builder(activity).apply {
            setView(view).setPositiveButton(R.string.accept) { _, _ -> dialog.cancel() }
        }.create()
    }

}