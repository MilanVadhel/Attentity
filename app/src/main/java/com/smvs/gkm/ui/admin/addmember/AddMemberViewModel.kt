package com.smvs.gkm.ui.admin.addmember

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.smvs.gkm.common.extensions.*
import com.smvs.gkm.common.utils.ApiKeyUtils
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.domain.admin.repository.AdminRepository
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewModel
import com.smvs.gkm.ui.admin.addmember.validationenum.AddMemberValidation
import com.smvs.gkm.ui.common.base.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AddMemberViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : BaseViewModel() {

    private val _addMemberValidationLiveEvent: SingleLiveEvent<AddMemberValidation> =
        SingleLiveEvent()
    val addMemberValidationLiveEvent: LiveData<AddMemberValidation> get() = _addMemberValidationLiveEvent

    private val _usersLiveEvent: SingleLiveEvent<UiState<ArrayList<User>>> = SingleLiveEvent()
    val usersLiveEvent: LiveData<UiState<ArrayList<User>>> get() = _usersLiveEvent

    private val _addMemberLiveEvent: SingleLiveEvent<UiState<Unit>> = SingleLiveEvent()
    val addMemberLiveEvent: LiveData<UiState<Unit>> get() = _addMemberLiveEvent

    private val _editMemberLiveEvent: SingleLiveEvent<UiState<Unit>> = SingleLiveEvent()
    val editMemberLiveEvent: LiveData<UiState<Unit>> get() = _editMemberLiveEvent

    var selectedUserId: Int = -1
        get() = field
        set(value) {
            field = value
        }

    fun addEditMember(
        firstName: String,
        lastName: String,
        email: String,
        contact: String,
        assignedTo: Int = -1,
        isForUpdate: Boolean,
        memberId: Int = -1
    ) {
        when {
            firstName.isNullOrEmpty() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.FIRST_NAME_EMPTY
            }
            lastName.isNullOrEmpty() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.LAST_NAME_EMPTY
            }
            email.isNullOrEmpty() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.EMAIL_EMPTY
            }
            !email.isValidEmail() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.INVALID_EMAIL
            }
            contact.isNullOrEmpty() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.CONTACT_EMPTY
            }
            contact.isNotValidMobileNumber() -> {
                _addMemberValidationLiveEvent.value = AddMemberValidation.INVALID_CONTACT
            }
//            assignedTo == -1 -> {
//                _addMemberValidationLiveEvent.value = AddMemberValidation.NOT_ASSIGNED_TO_USER
//            }
            else -> {
                if (isForUpdate && memberId !== -1) {
                    editMember(memberId, firstName, lastName, email, contact, assignedTo)
                } else {
                    addMember(firstName, lastName, email, contact, assignedTo)
                }
            }
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            try {
                _usersLiveEvent.setLoading()
                val apiResponse = adminRepository.getUsers()
                apiResponse.data?.let { usersList ->
                    if (usersList.isEmpty()) {
                        _usersLiveEvent.setEmpty()
                    } else {
                        _usersLiveEvent.setSuccess(usersList)
                    }
                }
            } catch (exception: Exception) {
                _usersLiveEvent.setError(exception)
            }
        }
    }

    private fun addMember(
        firstName: String,
        lastName: String,
        email: String,
        contact: String,
        assignedTo: Int
    ) {
        viewModelScope.launch {
            try {
                _addMemberLiveEvent.setLoading()
                val requestMap = hashMapOf<String, Any>(
                    ApiKeyUtils.KEY_FIRST_NAME to firstName,
                    ApiKeyUtils.KEY_LAST_NAME to lastName,
                    ApiKeyUtils.KEY_EMAIL to email,
                    ApiKeyUtils.KEY_CONTACT to contact,
                )
                if (assignedTo !== -1) {
                    requestMap[ApiKeyUtils.KEY_ASSIGN_TO] = assignedTo
                }
                val apiResponse = adminRepository.addMember(
                    requestMap
                )
                apiResponse.data?.let { _ ->
                    _addMemberLiveEvent.setSuccess(Unit)
                }
            } catch (e: Exception) {
                _addMemberLiveEvent.setError(e)
            }
        }
    }

    private fun editMember(
        memberId: Int,
        firstName: String,
        lastName: String,
        email: String,
        contact: String,
        assignedTo: Int
    ) {
        viewModelScope.launch {
            try {
                _editMemberLiveEvent.setLoading()
                val requestMap = hashMapOf<String, Any>(
                    ApiKeyUtils.KEY_FIRST_NAME to firstName,
                    ApiKeyUtils.KEY_LAST_NAME to lastName,
                    ApiKeyUtils.KEY_EMAIL to email,
                    ApiKeyUtils.KEY_CONTACT to contact,
                )
                if (assignedTo !== -1) {
                    requestMap[ApiKeyUtils.KEY_ASSIGN_TO] = assignedTo
                }
                val apiResponse = adminRepository.editMember(
                    memberId,
                    requestMap
                )
                apiResponse.data?.let { _ ->
                    _editMemberLiveEvent.setSuccess(Unit)
                }
            } catch (e: Exception) {
                _editMemberLiveEvent.setError(e)
            }
        }
    }
}