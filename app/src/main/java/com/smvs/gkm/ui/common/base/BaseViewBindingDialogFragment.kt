package com.smvs.gkm.ui.common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding

abstract class BaseViewBindingDialogFragment<VB : ViewBinding> : DialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(STYLE_NO_TITLE, R.style.BaseDialogFragment)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateLayout(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    protected open fun initViews() = Unit

    abstract fun inflateLayout(inflater: LayoutInflater): VB

//    fun findAppMainNavController(): NavController {
//        val navHostFragment =
//            activity?.supportFragmentManager?.findFragmentById(R.id.navDrawerHostFragment) as NavHostFragment
//        return navHostFragment.navController
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}