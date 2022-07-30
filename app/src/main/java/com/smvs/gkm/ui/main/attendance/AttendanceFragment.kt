package com.smvs.gkm.ui.main.attendance

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.*
import com.smvs.gkm.common.utils.*
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance
import com.smvs.gkm.databinding.FragmentAttendanceBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import com.smvs.gkm.ui.main.attendance.adapter.AssignedMembersAttendanceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AttendanceFragment : BaseViewBindingFragment<FragmentAttendanceBinding>(),
    View.OnClickListener {

    private val viewModel: AttendanceViewModel by viewModels()

    private val assignedMembersAttendanceAdapter: AssignedMembersAttendanceAdapter by lazy {
        AssignedMembersAttendanceAdapter(::handleSaveAttendance)
    }

    private fun handleSaveAttendance(present: Boolean, memberId: Int, report: Boolean) {
        AlertUtils.showAlert(requireContext(), R.string.text_are_you_sure_you_want_to_save, {
            viewModel.saveAttendance(memberId, present, report)
        })
    }

    override fun getClassName(): String = AttendanceFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAttendanceBinding {
        return FragmentAttendanceBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        initListeners()
        setToolbarTitle(getString(R.string.text_attendance))
        initAttendeesRecyclerView()
        initApiCalls()
    }

    private fun initListeners() {
        binding.apply {
            ilAttendanceHeader.ibBack.setOnClickListener(this@AttendanceFragment)
        }
    }

    private fun initApiCalls() {
        viewModel.apply {
            getUserInfo()
            getAssignedMembersAttendances()
        }
    }

    override fun initObservers() {
        super.initObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.assignedMembersAttendanceLiveEvent.safeObserve(
                viewLifecycleOwner,
                ::handleAssignedMembersAttendanceLiveEvent
            )
            viewModel.userInfoLiveEvent.safeObserve(viewLifecycleOwner, ::handleUserInfoLiveEvent)
            viewModel.saveAttendanceLiveEvent.safeObserve(
                viewLifecycleOwner,
                ::handleSaveAttendanceLiveEvent
            )
        }
    }

    private fun handleUserInfoLiveEvent(hashMap: HashMap<String, String>) {
        binding.apply {
            tvUserName.text = getString(
                R.string.text_welcome,
                hashMap[ApiKeyUtils.KEY_FIRST_NAME],
                hashMap[ApiKeyUtils.KEY_LAST_NAME]
            )
            tvPleaseFill.text =
                getString(R.string.text_please_fill, getCurrentDate(APP_DATE_FORMAT1))
        }
    }

    private fun handleSaveAttendanceLiveEvent(uiState: UiState<String>) {
        when (uiState) {
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.data)
                viewModel.getAssignedMembersAttendances()
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
        }
    }

    private fun handleAssignedMembersAttendanceLiveEvent(uiState: UiState<ArrayList<AssignedMemberAttendance>>) {
        when (uiState) {
            is UiState.Empty -> {
                Timber.d("No attendees")
                ProgressDialogUtil.hideProgressDialog()
                binding.apply {
                    tvNoAttendees.show()
                    rvAttendees.hide()
                }
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                binding.rvAttendees.hide()
                view.showSnackBar(message = uiState.throwable.localizedMessage)
            }
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                binding.apply {
                    tvNoAttendees.invisible()
                    rvAttendees.show()
                    assignedMembersAttendanceAdapter.addAll(uiState.data, true)
                }
            }
        }
    }

    private fun initAttendeesRecyclerView() {
        binding.rvAttendees.adapter = assignedMembersAttendanceAdapter
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ibBack -> {
                findNavController().popBackStack()
            }
        }
    }

}