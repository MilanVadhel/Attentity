package com.smvs.gkm.ui.lrf.login

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.common.utils.showHidePassword
import com.smvs.gkm.databinding.FragmentLoginBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.admin.AdminActivity
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import com.smvs.gkm.ui.lrf.login.loginenum.LoginValidation
import com.smvs.gkm.ui.lrf.login.loginenum.Role
import com.smvs.gkm.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseViewBindingFragment<FragmentLoginBinding>(), View.OnClickListener {

    private val viewModel: LoginViewModel by viewModels()

    override fun getClassName(): String = LoginFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        initListeners()
    }

    private fun initListeners() {
        binding.apply {
            btnSignIn.setOnClickListener(this@LoginFragment)
            tvForgotPassword.setOnClickListener(this@LoginFragment)
            tvContactUs.setOnClickListener(this@LoginFragment)
            ivPasswordToggle.setOnClickListener(this@LoginFragment)
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.apply {
            loginValidationLiveEvent.safeObserve(viewLifecycleOwner, ::handleValidationLiveEvent)
            loginLiveEvent.safeObserve(viewLifecycleOwner, ::handleLoginLiveEvent)
        }
    }

    private fun handleLoginLiveEvent(uiState: UiState<String>) {
        when (uiState) {
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                if (uiState.data == Role.ADMIN.role) {
                    navigateToAdminActivity()
                } else {
                    navigateToMainActivity()
                }
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
        }
    }

    private fun navigateToAdminActivity() {
        val intent = Intent(requireContext(), AdminActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun handleValidationLiveEvent(loginValidation: LoginValidation) {
        when (loginValidation) {
            LoginValidation.ID_EMPTY -> view.showSnackBar(getString(R.string.error_please_enter_your_user_id))
            LoginValidation.PASSWORD_EMPTY -> view.showSnackBar(getString(R.string.error_please_enter_your_password))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnSignIn -> {
                viewModel.login(
                    binding.etUserName.text.toString().trim(),
                    binding.etPassword.text.toString().trim(),
                    binding.etCenter.text.toString().trim()
                )
            }
            R.id.tvForgotPassword -> {
            }
            R.id.tvContactUs -> {
            }
            R.id.ivPasswordToggle -> {
                showHidePassword(binding.etPassword, binding.ivPasswordToggle)
            }
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}