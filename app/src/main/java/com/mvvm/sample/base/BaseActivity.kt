package com.mvvm.sample.base

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.mvvm.sample.R
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

abstract class BaseActivity<VM: BaseViewModel,DB: ViewDataBinding> : AppCompatActivity(), IBaseView {

    protected lateinit var  viewModel: VM
    protected lateinit var dataBinding: DB

    abstract fun getLayoutId(): Int
    abstract fun getViewModelClass(): Class<VM>
    abstract fun getVariablesToBind(): Map<Int,Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        initViewModel()
        initView()
    }

    open fun initViewModel() {
        viewModel = obtainViewModel(getViewModelClass())
    }

    open fun initView() {
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        dataBinding.setLifecycleOwner(this)
        for ((variableId,value) in getVariablesToBind()) {
            dataBinding.setVariable(variableId,value)
        }
        dataBinding.executePendingBindings()
    }

    fun setToolbarTitle(textInt: Int) = toolbar?.setTitle(textInt)

    override fun isActive(): Boolean = !isFinishing

    override fun showAlert(textResource: Int) {
        if (isActive()) {
            alert(textResource) {
                yesButton { dialog -> dialog.dismiss() }
            }.show()
        }
    }

    override fun getViewContext(): Context = this


    fun replaceFragment(fragment: Fragment, @IdRes fragmentId: Int, tag: String) {
        try {
            if (isNewFragment(fragmentId, tag)) {
                supportFragmentManager.beginTransaction().replace(fragmentId, fragment, tag).commit()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun isNewFragment(@IdRes fragmentId: Int, tag: String): Boolean = !getCurrentFragment(fragmentId)?.tag.equals(tag)

    private fun getCurrentFragment(@IdRes fragmentId: Int): Fragment? = supportFragmentManager.findFragmentById(fragmentId)

    override fun <T : BaseViewModel> obtainViewModel(clazz: Class<T>): T = ViewModelProviders.of(this).get(clazz)

    override fun onNetworkError() = showAlert(R.string.error_network)
}