package com.mvvm.sample.base

import android.app.Dialog
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import com.mvvm.sample.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseDialogFragment<VM: BaseViewModel,DB: ViewDataBinding>: DialogFragment(), IBaseView {

    private lateinit var fragmentContext: Context

    protected lateinit var dataBinding: DB
    protected lateinit var viewModel: VM

    abstract fun getLayoutId() : Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getVariablesToBind(): Map<Int,Any>

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        initViewModel()
        initView()
        return super.onCreateDialog(savedInstanceState)
    }

    open fun initViewModel() {
        viewModel = obtainViewModel(getViewModelClass())
    }

    open fun initView() {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(getViewContext()), getLayoutId(), null,false)
        dataBinding.setLifecycleOwner(this)
        for ((variableId,value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId,value)
        }
        dataBinding.executePendingBindings()
    }

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

    override fun onNetworkError() = showAlert(R.string.error_network)
}