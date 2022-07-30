package com.smvs.gkm.ui.admin.dashboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.smvs.gkm.ui.admin.dashboard.dashboardenum.DashboardTabs
import com.smvs.gkm.ui.admin.dashboard.members.MembersFragment
import com.smvs.gkm.ui.admin.dashboard.users.UsersFragment

class DashboardPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return DashboardTabs.values().size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> UsersFragment()
            1 -> MembersFragment()
            else -> UsersFragment()
        }
    }
}