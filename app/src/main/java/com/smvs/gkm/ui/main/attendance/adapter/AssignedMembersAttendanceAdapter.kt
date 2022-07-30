package com.smvs.gkm.ui.main.attendance.adapter

import android.view.ViewGroup
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.toBinding
import com.smvs.gkm.common.utils.AlertUtils
import com.smvs.gkm.data.main.attendance.entity.AssignedMemberAttendance
import com.smvs.gkm.databinding.ItemAttendeeBinding
import com.smvs.gkm.ui.common.base.BaseRecyclerViewAdapter
import com.smvs.gkm.ui.common.base.BaseRecyclerViewHolder

class AssignedMembersAttendanceAdapter(
    val saveAttendance: (isPresent: Boolean, memberId: Int, isReport: Boolean) -> Unit,
) : BaseRecyclerViewAdapter<AssignedMemberAttendance, AssignedMembersAttendanceAdapter.ReportViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ReportViewHolder(
        private val itemBinding: ItemAttendeeBinding
    ) : BaseRecyclerViewHolder<AssignedMemberAttendance>(itemBinding) {
        override fun bind(item: AssignedMemberAttendance) {
            itemBinding.apply {
                tvAttendeeName.text = itemBinding.root.context.getString(
                    R.string.text_user_full_name,
                    item.firstName,
                    item.lastName
                )
                if (item.attendances.isNullOrEmpty()) {
                    // Not Added Attendance Yet
                    itemBinding.apply {
                        rbAbsent.isChecked = true
                        rbPresent.isChecked = false
                        cbReport.isChecked = false
                    }
                } else {
                    // Added Attendance
                    itemBinding.apply {
                        if (item.attendances[0].present) {
                            rbPresent.isChecked = true
                            rbAbsent.isChecked = false
                        } else {
                            rbAbsent.isChecked = true
                            rbPresent.isChecked = false
                        }
                        cbReport.isChecked = item.attendances[0].report
                        cbReport.isEnabled = !item.attendances[0].report
                    }
                }
                cbReport.setOnCheckedChangeListener { _, isChecked ->
                    if(isChecked){
                        AlertUtils.showAlert(itemBinding.root.context,R.string.text_are_you_sure_you_want_to_report,{
                            cbReport.isChecked = true
                        },{
                            cbReport.isChecked = false
                        })
                    }
                }
                btnSave.setOnClickListener {
                    saveAttendance(
                        rbPresent.isChecked,
                        item.id,
                        cbReport.isChecked
                    )
                }
            }
        }
    }
}