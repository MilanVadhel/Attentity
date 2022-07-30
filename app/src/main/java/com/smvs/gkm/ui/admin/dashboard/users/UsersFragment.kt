package com.smvs.gkm.ui.admin.dashboard.users

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import com.smvs.gkm.common.extensions.invisible
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.extensions.show
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.databinding.FragmentUsersBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.admin.dashboard.users.adapter.UsersAdapter
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersFragment : BaseViewBindingFragment<FragmentUsersBinding>() {

    private val viewModel: UsersViewModel by viewModels()

    private val usersAdapter: UsersAdapter by lazy {
        UsersAdapter()
    }

    override fun getClassName(): String = UsersFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentUsersBinding {
        return FragmentUsersBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        binding.rcvUsers.adapter = usersAdapter
        initApiCalls()
        initListeners()
    }

    private fun initListeners() {
        binding.slUsers.setOnRefreshListener {
            initApiCalls()
        }
    }

    private fun initApiCalls() {
        viewModel.getUsers()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.usersLiveEvent.safeObserve(viewLifecycleOwner, ::handleUsersLiveEvent)
    }

    private fun handleUsersLiveEvent(uiState: UiState<ArrayList<User>>) {
        when (uiState) {
            is UiState.Empty -> {
                binding.tvNoUsers.show()
                binding.slUsers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
            }
            is UiState.Error -> {
                binding.tvNoUsers.invisible()
                binding.slUsers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
            is UiState.Loading -> {
                binding.tvNoUsers.invisible()
                binding.slUsers.isRefreshing = true
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                binding.tvNoUsers.invisible()
                binding.slUsers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
                usersAdapter.addAll(uiState.data, true)
            }
        }
    }
}