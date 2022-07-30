package com.smvs.gkm.ui.lrf

import android.view.LayoutInflater
import com.smvs.gkm.databinding.ActivityLrfBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LrfActivity : BaseViewBindingActivity<ActivityLrfBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityLrfBinding {
        return ActivityLrfBinding.inflate(layoutInflater)
    }
}