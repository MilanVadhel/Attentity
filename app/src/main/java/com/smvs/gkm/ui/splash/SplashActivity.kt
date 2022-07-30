package com.smvs.gkm.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.databinding.ActivitySplashBinding
import com.smvs.gkm.ui.admin.AdminActivity
import com.smvs.gkm.ui.common.base.BaseActivity
import com.smvs.gkm.ui.common.base.BaseViewBindingActivity
import com.smvs.gkm.ui.lrf.LrfActivity
import com.smvs.gkm.ui.lrf.login.loginenum.Role
import com.smvs.gkm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseViewBindingActivity<ActivitySplashBinding>() {

    private val viewModel: SplashViewModel by viewModels()

    override fun initViews() {
        super.initViews()
        viewModel.isLogin()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.apply {
            isLoginLiveEvent.safeObserve(this@SplashActivity, ::handleIsLoginLiveEvent)
        }
    }

    private fun handleIsLoginLiveEvent(hashMap: HashMap<String, Any>) {
        if (hashMap[ApiKeyUtils.KEY_IS_LOGIN] as Boolean) {
            if (hashMap[ApiKeyUtils.KEY_ROLE] == Role.ADMIN.role) {
                // User
                startActivity(Intent(this, AdminActivity::class.java))
            } else {
                // Member
                startActivity(Intent(this, MainActivity::class.java))
            }
        } else {
            startActivity(Intent(this, LrfActivity::class.java))
        }
        finish()
    }

    override fun inflateLayout(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}