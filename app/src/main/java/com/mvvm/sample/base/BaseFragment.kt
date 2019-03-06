package com.mvvm.sample.base

import androidx.lifecycle.ViewModelProviders
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mvvm.sample.R
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseFragment<VM: BaseViewModel,DB: ViewDataBinding>: Fragment(), IBaseView {

    private lateinit var fragmentContext: Context

    protected lateinit var dataBinding: DB
    protected lateinit var viewModel: VM

    abstract fun getLayoutId() : Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getVariablesToBind(): Map<Int,Any>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initViewModel()
        initView(inflater, container)
        return dataBinding.root
    }

    open fun initViewModel() {
        viewModel = obtainViewModel(getViewModelClass())
    }

    open fun initView(inflater: LayoutInflater, container: ViewGroup?) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
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