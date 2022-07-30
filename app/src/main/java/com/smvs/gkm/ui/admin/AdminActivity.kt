package com.smvs.gkm.ui.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.smvs.gkm.R
import com.smvs.gkm.databinding.ActivityAdminBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminActivity : BaseViewBindingActivity<ActivityAdminBinding>() {
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityAdminBinding {
        return ActivityAdminBinding.inflate(layoutInflater)
    }
}