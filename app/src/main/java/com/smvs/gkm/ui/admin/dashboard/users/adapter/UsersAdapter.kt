package com.smvs.gkm.ui.admin.dashboard.users.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.hide
import com.smvs.gkm.common.extensions.invisible
import com.smvs.gkm.common.extensions.toBinding
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.databinding.ItemUserBinding
import com.smvs.gkm.ui.common.base.BaseRecyclerViewAdapter
import com.smvs.gkm.ui.common.base.BaseRecyclerViewHolder

class UsersAdapter(
) : BaseRecyclerViewAdapter<User, UsersAdapter.UsersViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class UsersViewHolder(
        private val itemBinding: ItemUserBinding
    ) : BaseRecyclerViewHolder<User>(itemBinding) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: User) {
            itemBinding.apply {
                item.userDetails?.let { userDetails ->
                    tvUserFirstNameLatter.text = userDetails.firstName[0].toString()
                    tvUserFirstNameLatter.isEnabled = adapterPosition % 2 == 0
                    tvUserFullName.text = "${userDetails.firstName} ${userDetails.lastName}"
                    tvAssignedNotAssignedLabel.invisible()
                }
            }
        }
    }
}