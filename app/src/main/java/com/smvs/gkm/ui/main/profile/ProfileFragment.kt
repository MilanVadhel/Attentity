package com.smvs.gkm.ui.main.profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smvs.gkm.R
import com.smvs.gkm.common.utils.AlertUtils
import com.smvs.gkm.databinding.FragmentProfileBinding
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import com.smvs.gkm.ui.lrf.LrfActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseViewBindingFragment<FragmentProfileBinding>(), View.OnClickListener {

    private val viewModel: ProfileViewModel by viewModels()

    override fun getClassName(): String = ProfileFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        initListeners()
        setToolbarTitle(getString(R.string.text_profile))
    }

    private fun initListeners() {
        binding.apply {
            vSignOut.setOnClickListener(this@ProfileFragment)
            ilProfileHeader.ibBack.setOnClickListener(this@ProfileFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.vSignOut -> {
                logout()
            }
            R.id.ibBack -> {
                findNavController().popBackStack()
            }
        }
    }

    private fun logout() {
        AlertUtils.showAlert(
            context = requireContext(),
            message = R.string.text_are_you_sure_you_want_to_logout,
            positiveButtonCallback =
            {
                viewModel.logout()
                startActivity(Intent(requireContext(), LrfActivity::class.java))
                requireActivity().finish()
            }
        )
    }
}