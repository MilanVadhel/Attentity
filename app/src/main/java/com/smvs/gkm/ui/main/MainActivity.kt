package com.smvs.gkm.ui.main

import android.view.LayoutInflater
import com.smvs.gkm.databinding.ActivityMainBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseViewBindingActivity<ActivityMainBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}