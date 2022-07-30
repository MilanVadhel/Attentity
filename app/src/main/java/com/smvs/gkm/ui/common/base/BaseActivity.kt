package com.smvs.gkm.ui.common.base

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.viewbinding.ViewBinding
import com.smvs.gkm.common.enums.NetworkStatus
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.common.utils.checkNetworkConnection
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.ui.lrf.LrfActivity
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!
    private val viewModel: BaseViewModel by viewModels()

    @Inject
    lateinit var authRepository: AuthRepository

    private val baseViewModel: BaseViewModel by viewModels()

    private val unAuthorizedBroadCast = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LrfActivity::class.java))
        finish()
        lifecycleScope.launchWhenCreated {
            authRepository.logout()
        }
    }

    private val connectivityManager: ConnectivityManager by lazy {
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private val networkRequestBuilder: NetworkRequest.Builder by lazy {
        NetworkRequest.Builder()
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                onInternetConnectionChange(NetworkStatus.NETWORK_ON)
            }

            override fun onLost(network: Network) {
                onInternetConnectionChange(NetworkStatus.NETWORK_OFF)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflateLayout(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObserver()
    }

    protected open fun initObserver() {
    }

    private fun showNoInternetActivity() {
        checkNetworkConnection(this, NetworkStatus.NETWORK_OFF)
    }

    private fun hideNoInternetActivity() {
        checkNetworkConnection(this, NetworkStatus.NETWORK_ON)
    }

    override fun onResume() {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), networkCallback)
        LocalBroadcastManager.getInstance(this).registerReceiver(
            unAuthorizedBroadCast,
            IntentFilter(ApiKeyUtils.UN_AUTHORIZED_LOGOUT)
        )
        checkNetworkAndShowNoInternetActivity()
        super.onResume()
    }

    private fun checkNetworkAndShowNoInternetActivity() {
        if (connectivityManager.activeNetwork == null) {
            showNoInternetActivity()
        }
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(networkCallback)
        LocalBroadcastManager.getInstance(this).unregisterReceiver(unAuthorizedBroadCast)
    }

    @CallSuper
    protected open fun initViews() {
    }

    abstract fun inflateLayout(layoutInflater: LayoutInflater): VB

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    @CallSuper
    protected open fun onInternetConnectionChange(networkStatus: NetworkStatus) {
        checkNetworkConnection(this, networkStatus)
    }
}