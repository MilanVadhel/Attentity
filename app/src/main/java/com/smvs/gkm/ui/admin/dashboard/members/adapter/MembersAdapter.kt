package com.smvs.gkm.ui.admin.dashboard.members.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.toBinding
import com.smvs.gkm.common.utils.AlertUtils
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.databinding.ItemUserBinding
import com.smvs.gkm.ui.common.base.BaseRecyclerViewAdapter
import com.smvs.gkm.ui.common.base.BaseRecyclerViewHolder

class MembersAdapter(
    val onMemberClick: (member: UserDetails) -> Unit,
    val onMemberDelete: (member: UserDetails) -> Unit,
) : BaseRecyclerViewAdapter<UserDetails, MembersAdapter.MembersViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): MembersViewHolder {
        return MembersViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: MembersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MembersViewHolder(
        private val itemBinding: ItemUserBinding
    ) : BaseRecyclerViewHolder<UserDetails>(itemBinding) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: UserDetails) {
            itemBinding.apply {
                tvUserFirstNameLatter.text = item.firstName[0].toString()
                tvUserFirstNameLatter.isEnabled = adapterPosition % 2 == 0
                tvUserFullName.text = "${item.firstName} ${item.lastName}"
                clMember.setOnClickListener {
                    onMemberClick(item)
                }
                clMember.setOnLongClickListener {
                    AlertUtils.showAlert(
                        clMember.context,
                        R.string.text_are_you_sure_you_want_to_delete,
                        {
                            onMemberDelete(item)
                        })
                    return@setOnLongClickListener true
                }
                if (item.assignedUser == null) {
                    // Not Assigned
                    tvAssignedNotAssignedLabel.text =
                        itemBinding.root.context.getString(R.string.text_not_assigned)
                    tvAssignedNotAssignedLabel.isEnabled = false
                } else {
                    // Assigned
                    tvAssignedNotAssignedLabel.text =
                        itemBinding.root.context.getString(R.string.text_assigned)
                    tvAssignedNotAssignedLabel.isEnabled = true
                }
            }
        }
    }
}