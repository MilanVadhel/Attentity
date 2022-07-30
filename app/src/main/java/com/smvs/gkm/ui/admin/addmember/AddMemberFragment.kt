package com.smvs.gkm.ui.admin.addmember

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.databinding.FragmentAddMemberBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.admin.AdminViewModel
import com.smvs.gkm.ui.admin.addmember.validationenum.AddMemberValidation
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import com.smvs.gkm.ui.common.base.MySpinnerItemSelectionListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class AddMemberFragment : BaseViewBindingFragment<FragmentAddMemberBinding>(),
    View.OnClickListener {

    private val args: AddMemberFragmentArgs by navArgs()

    private val viewModel: AddMemberViewModel by viewModels()

    private val adminViewModel: AdminViewModel by activityViewModels()

    override fun getClassName(): String = AddMemberFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentAddMemberBinding {
        return FragmentAddMemberBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setData()
        initListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setData() {
        args.userInfo?.let {
            binding.apply {
                etFirstName.setText(it.firstName)
                etLastname.setText(it.lastName)
                etEmail.setText(it.email)
                etContact.setText(it.contact)
                if (it.assignedUser !== null) {
                    etAssignTo.setText("${it.assignedUser.userDetails?.firstName} ${it.assignedUser.userDetails?.lastName}")
                }
                if (args.isForUpdate) {
                    btnAddUpdate.text = getString(R.string.text_update)
                } else {
                    btnAddUpdate.text = getString(R.string.text_add)
                }
                adminViewModel.selectedUserId = args.userInfo?.id ?: -1
            }
        }
    }

    private fun initListeners() {
        binding.apply {
            btnAddUpdate.setOnClickListener(this@AddMemberFragment)
            etAssignTo.setOnClickListener(this@AddMemberFragment)
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.btnAddUpdate -> {
                handleAddUpdateMember()
            }
            R.id.etAssignTo -> {
                UsersBottomSheet.getBottomSheetInstance().show(childFragmentManager, "TAG")
            }
        }
    }

    private fun handleAddUpdateMember() {
        viewModel.addEditMember(
            firstName = binding.etFirstName.text.toString().trim(),
            lastName = binding.etLastname.text.toString().trim(),
            email = binding.etEmail.text.toString().trim(),
            contact = binding.etContact.text.toString().trim(),
            assignedTo = viewModel.selectedUserId,
            isForUpdate = args.isForUpdate,
            memberId = adminViewModel.selectedUserId
        )
    }

    override fun initObservers() {
        super.initObservers()
        viewModel.apply {
            addMemberValidationLiveEvent.safeObserve(
                viewLifecycleOwner,
                ::handleAddMemberValidationLiveEvent
            )
            addMemberLiveEvent.safeObserve(viewLifecycleOwner, ::handleAddMemberLiveEvent)
            editMemberLiveEvent.safeObserve(viewLifecycleOwner, ::handleAddMemberLiveEvent)
            //usersLiveEvent.safeObserve(viewLifecycleOwner, ::handleUsersLiveEvent)
        }
    }

    private fun handleAddMemberLiveEvent(uiState: UiState<Unit>) {
        when (uiState) {
            is UiState.Loading -> {
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Success -> {
                ProgressDialogUtil.hideProgressDialog()
                findNavController().popBackStack()
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                view.showSnackBar(message = uiState.throwable.message)
            }
        }
    }

    private fun handleAddMemberValidationLiveEvent(addMemberValidation: AddMemberValidation) {
        when (addMemberValidation) {
            AddMemberValidation.FIRST_NAME_EMPTY -> {
                view.showSnackBar(message = R.string.text_please_enter_first_name)
            }
            AddMemberValidation.LAST_NAME_EMPTY -> {
                view.showSnackBar(message = R.string.text_please_enter_last_name)
            }
            AddMemberValidation.CONTACT_EMPTY -> {
                view.showSnackBar(message = R.string.text_please_enter_contact_number)
            }
            AddMemberValidation.INVALID_CONTACT -> {
                view.showSnackBar(message = R.string.text_please_enter_valid_contact)
            }
            AddMemberValidation.EMAIL_EMPTY -> {
                view.showSnackBar(message = R.string.text_please_enter_email)
            }
            AddMemberValidation.INVALID_EMAIL -> {
                view.showSnackBar(message = R.string.text_please_enter__valid_email)
            }
            AddMemberValidation.NOT_ASSIGNED_TO_USER -> {

            }
        }
    }
}