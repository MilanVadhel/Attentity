package com.smvs.gkm.ui.admin.addmember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.databinding.UsersBottomSheetLayoutBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.admin.AdminViewModel
import com.smvs.gkm.ui.admin.addmember.adapter.MemberSelectionAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersBottomSheet : BottomSheetDialogFragment() {

    private var _binding: UsersBottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    private val usersBottomSheetViewModel: UsersBottomSheetViewModel by viewModels()
    private val adminViewModel: AdminViewModel by activityViewModels()

    private val membersAdapter: MemberSelectionAdapter by lazy {
        MemberSelectionAdapter(::handleOnMemberClick)
    }

    private fun handleOnMemberClick(userDetails: User) {
        adminViewModel.selectedUserId = userDetails.id
        this@UsersBottomSheet.dismiss()
    }

    companion object {
        fun getBottomSheetInstance(): UsersBottomSheet {
            return UsersBottomSheet()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UsersBottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        initObserver()
        initApiCalls()
    }

    private fun setUpRecyclerView() {
        binding.rvUsers.adapter = membersAdapter
    }

    private fun initObserver() {
        usersBottomSheetViewModel.usersLiveEvent.safeObserve(
            viewLifecycleOwner,
            ::handleUsersLiveEvent
        )
    }

    private fun handleUsersLiveEvent(uiState: UiState<ArrayList<User>>) {
        when (uiState) {
            is UiState.Empty -> {
                ProgressDialogUtil.hideProgressDialog()
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                membersAdapter.addAll(uiState.data, true)
            }
        }
    }

    private fun initApiCalls() {
        usersBottomSheetViewModel.getUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
