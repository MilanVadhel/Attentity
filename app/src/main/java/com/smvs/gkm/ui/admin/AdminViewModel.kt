package com.smvs.gkm.ui.admin

import com.smvs.gkm.domain.admin.repository.AdminRepository
import com.smvs.gkm.ui.common.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdminViewModel @Inject constructor(
    private val adminRepository: AdminRepository
) : BaseViewModel() {

    var selectedUserId: Int = -1
        get() = field
        set(value) {
            field = value
        }

}