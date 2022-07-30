package com.smvs.gkm.ui.admin.dashboard

import android.view.LayoutInflater
import com.google.android.material.tabs.TabLayoutMediator
import com.smvs.gkm.databinding.FragmentAdminDashboardBinding
import com.smvs.gkm.ui.admin.dashboard.adapter.DashboardPagerAdapter
import com.smvs.gkm.ui.admin.dashboard.dashboardenum.DashboardTabs
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminDashboardFragment : BaseViewBindingFragment<FragmentAdminDashboardBinding>() {

    private lateinit var dashboardPagerAdapter: DashboardPagerAdapter

    override fun getClassName(): String = AdminDashboardFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAdminDashboardBinding {
        return FragmentAdminDashboardBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setUpViewPager()
    }

    private fun setUpViewPager() {
        dashboardPagerAdapter = DashboardPagerAdapter(requireActivity())
        binding.vpUsers.adapter = dashboardPagerAdapter
        TabLayoutMediator(binding.tblUsers, binding.vpUsers) { tab, position ->
            tab.text = getString(DashboardTabs.values()[position].tabName)
        }.attach()
    }
}