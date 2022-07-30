package com.smvs.gkm.ui.admin.landing

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.loadImage
import com.smvs.gkm.common.extensions.safeNavigate
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.data.main.landing.entity.Option
import com.smvs.gkm.databinding.FragmentAdminLandingBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AdminLandingFragment : BaseViewBindingFragment<FragmentAdminLandingBinding>() {

    private val adminLandingViewModel: AdminLandingViewModel by viewModels()

    override fun getClassName(): String = AdminLandingFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAdminLandingBinding {
        return FragmentAdminLandingBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setData()
        initListeners()
        initApiCalls()
    }

    private fun initApiCalls() {
        adminLandingViewModel.getCenterName()
    }

    override fun initObservers() {
        super.initObservers()
        adminLandingViewModel.centerNameLiveEvent.safeObserve(
            viewLifecycleOwner,
            ::handleCenterNameLiveEvent
        )
    }

    private fun handleCenterNameLiveEvent(centerName: String) {
        binding.tvCenterName.text = centerName
    }

    private fun initListeners() {
        binding.apply {
            ilAddMember.clOption.setOnClickListener {
                findNavController().safeNavigate(
                    AdminLandingFragmentDirections.actionAdminLandingFragmentToAddMemberFragment(
                        isForUpdate = false
                    )
                )
            }
            ilViewMembers.clOption.setOnClickListener {
                findNavController().safeNavigate(R.id.action_adminLandingFragment_to_usersListFragment2)
            }
            ilProfile.clOption.setOnClickListener {
                findNavController().safeNavigate(R.id.action_adminLandingFragment_to_profileFragment)
            }
        }
    }

    private fun setData() {
        binding.apply {
            ilAddMember.apply {
                ivOption.loadImage(Option.ADD_MEMBER.icon)
                tvOptionName.setText(Option.ADD_MEMBER.label)
            }
            ilViewMembers.apply {
                ivOption.loadImage(Option.VIEW_MEMBER.icon)
                tvOptionName.setText(Option.VIEW_MEMBER.label)
            }
            ilProfile.apply {
                ivOption.loadImage(Option.PROFILE.icon)
                tvOptionName.setText(Option.PROFILE.label)
            }
        }
    }
}