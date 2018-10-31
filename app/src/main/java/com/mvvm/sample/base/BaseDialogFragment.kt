package com.mvvm.sample.base

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v4.app.DialogFragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseDialogFragment: DialogFragment(), IBaseView {

    private lateinit var fragmentContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context
    }

    override fun isActive(): Boolean = isAdded

    override fun showAlert(textResource: Int) {
        activity?.let {
            if (isActive() && !it.isFinishing) {
                it.alert(textResource) {
                    yesButton { dialog -> dialog.dismiss() }
                }.show()
            }
        }
    }

    override fun getViewContext(): Context = fragmentContext

    override fun <T : BaseViewModel> obtainViewModel(clazz: Class<T>): T = ViewModelProviders.of(this).get(clazz)
}