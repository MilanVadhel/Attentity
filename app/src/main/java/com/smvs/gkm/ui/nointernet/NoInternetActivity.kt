package com.smvs.gkm.ui.nointernet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.smvs.gkm.R
import com.smvs.gkm.common.enums.NetworkStatus
import com.smvs.gkm.common.extensions.loadImage
import com.smvs.gkm.common.utils.checkNetworkConnection
import com.smvs.gkm.databinding.ActivityNoInternetBinding
import com.smvs.gkm.ui.common.base.BaseActivity

class NoInternetActivity : BaseActivity<ActivityNoInternetBinding>(), View.OnClickListener {
    var networkStatus: NetworkStatus = NetworkStatus.NETWORK_OFF
    override fun inflateLayout(layoutInflater: LayoutInflater): ActivityNoInternetBinding {
        return ActivityNoInternetBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.ivNoInternet.loadImage(R.drawable.ic_no_internet)
        binding.btnTryAgain.setOnClickListener(this)
    }

    override fun onInternetConnectionChange(networkStatus: NetworkStatus) {
        super.onInternetConnectionChange(networkStatus)
        checkNetworkConnection(this, networkStatus)
        this.networkStatus = networkStatus
    }

    override fun onBackPressed() {}

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnTryAgain -> if (networkStatus == NetworkStatus.NETWORK_ON)
                finish()
        }
    }
}