package com.smvs.gkm.ui.admin.dashboard.members

import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.smvs.gkm.common.extensions.invisible
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.extensions.show
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.databinding.FragmentMembersBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.admin.dashboard.AdminDashboardFragmentDirections
import com.smvs.gkm.ui.admin.dashboard.members.adapter.MembersAdapter
import com.smvs.gkm.ui.admin.landing.AdminLandingFragmentDirections
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MembersFragment : BaseViewBindingFragment<FragmentMembersBinding>() {

    private val viewModel: MembersViewModel by viewModels()

    private val membersAdapter: MembersAdapter by lazy {
        MembersAdapter(::handleOnMemberClick, ::handleMemberDelete)
    }

    private fun handleMemberDelete(userDetails: UserDetails) {
        viewModel.deleteMember(userDetails.id)
    }

    private fun handleOnMemberClick(member: UserDetails) {
        findNavController().navigate(
            AdminDashboardFragmentDirections.actionUsersListFragmentToAddMemberFragment(
                userInfo = member,
                isForUpdate = true
            )
        )
    }

    override fun getClassName(): String = MembersFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentMembersBinding {
        return FragmentMembersBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        initRecyclerView()
        initListeners()
        initApiCalls()
    }

    private fun initRecyclerView() {
        binding.rcvMembers.adapter = membersAdapter
    }

    private fun initApiCalls() {
        viewModel.getMembers()
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.membersLiveEvent.safeObserve(viewLifecycleOwner, ::handleUsersLiveEvent)
        viewModel.deleteMemberLiveEvent.safeObserve(
            viewLifecycleOwner,
            ::handleDeleteMemberLiveEvent
        )
    }

    private fun handleDeleteMemberLiveEvent(uiState: UiState<Unit>) {
        when (uiState) {
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                initApiCalls()
            }
        }
    }

    private fun initListeners() {
        binding.slMembers.setOnRefreshListener {
            initApiCalls()
        }
    }

    private fun handleUsersLiveEvent(uiState: UiState<ArrayList<UserDetails>>) {
        when (uiState) {
            is UiState.Empty -> {
                binding.tvNoMembers.show()
                binding.slMembers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
            }
            is UiState.Error -> {
                binding.tvNoMembers.invisible()
                binding.slMembers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
            is UiState.Loading -> {
                binding.tvNoMembers.invisible()
                binding.slMembers.isRefreshing = true
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                binding.tvNoMembers.invisible()
                binding.slMembers.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
                membersAdapter.addAll(uiState.data, true)
            }
        }
    }
}