package com.smvs.gkm.ui.main.landing

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.loadImage
import com.smvs.gkm.common.extensions.safeNavigate
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.data.main.landing.entity.Option
import com.smvs.gkm.databinding.FragmentLandingBinding
import com.smvs.gkm.databinding.FragmentLoginBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LandingFragment : BaseViewBindingFragment<FragmentLandingBinding>() {

    private val landingViewModel: LandingViewModel by viewModels()

    override fun getClassName(): String = LandingFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLandingBinding {
        return FragmentLandingBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setData()
        initListeners()
        initApiCalls()
    }

    private fun initApiCalls() {
        landingViewModel.getCenterName()
    }

    override fun initObservers() {
        super.initObservers()
        landingViewModel.centerNameLiveEvent.safeObserve(
            viewLifecycleOwner,
            ::handleCenterNameLiveEvent
        )
    }

    private fun handleCenterNameLiveEvent(centerName: String) {
        binding.tvCenterName.text = centerName
    }

    private fun initListeners() {
        binding.apply {
            ilMarkAttendance.clOption.setOnClickListener {
                findNavController().safeNavigate(R.id.action_landingFragment_to_attendanceFragment)
            }
            ilViewReport.clOption.setOnClickListener {
                findNavController().safeNavigate(R.id.action_landingFragment_to_reportFragment)
            }
            ilProfile.clOption.setOnClickListener {
                findNavController().safeNavigate(R.id.action_landingFragment_to_profileFragment)
            }
        }
    }

    private fun setData() {
        binding.apply {
            ilMarkAttendance.apply {
                ivOption.loadImage(Option.ATTENDANCE.icon)
                tvOptionName.setText(Option.ATTENDANCE.label)
            }
            ilViewReport.apply {
                ivOption.loadImage(Option.VIEW_REPORT.icon)
                tvOptionName.setText(Option.VIEW_REPORT.label)
            }
            ilProfile.apply {
                ivOption.loadImage(Option.PROFILE.icon)
                tvOptionName.setText(Option.PROFILE.label)
            }
        }
    }
}