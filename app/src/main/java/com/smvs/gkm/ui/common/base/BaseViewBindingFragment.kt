package com.smvs.gkm.ui.common.base

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingFragment<VB : ViewBinding> : BaseFragment<VB>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
    }

//    fun findAppMainNavController(): NavController {
//        val navHostFragment =
//            activity?.supportFragmentManager?.findFragmentById(R.id.navDrawerHostFragment) as NavHostFragment
//        return navHostFragment.navController
//    }

    @CallSuper
    protected open fun initObservers() = Unit

    protected open fun isMultipleLoad(): Boolean = false
}