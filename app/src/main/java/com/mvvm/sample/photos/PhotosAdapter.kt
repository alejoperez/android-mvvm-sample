package com.mvvm.sample.photos

import com.mvvm.sample.R
import com.mvvm.sample.base.BaseRecyclerViewAdapter
import com.mvvm.sample.data.room.Photo

class PhotosAdapter(photoList: List<Photo>?,listener: BaseRecyclerViewAdapter.OnItemClickListener) : BaseRecyclerViewAdapter(photoList,listener) {

    override fun getItemLayoutId(): Int = R.layout.item_photo

}