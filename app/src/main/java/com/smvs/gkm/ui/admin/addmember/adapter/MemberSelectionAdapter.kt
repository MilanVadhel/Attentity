package com.smvs.gkm.ui.admin.addmember.adapter

import android.annotation.SuppressLint
import android.view.ViewGroup
import com.smvs.gkm.common.extensions.toBinding
import com.smvs.gkm.data.auth.entity.User
import com.smvs.gkm.data.auth.entity.UserDetails
import com.smvs.gkm.databinding.BottomSheetItemBinding
import com.smvs.gkm.ui.common.base.BaseRecyclerViewAdapter
import com.smvs.gkm.ui.common.base.BaseRecyclerViewHolder

class MemberSelectionAdapter(
    val onMemberClick: (user: User) -> Unit,
) : BaseRecyclerViewAdapter<User, MemberSelectionAdapter.MemberSelectionViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): MemberSelectionViewHolder {
        return MemberSelectionViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: MemberSelectionViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class MemberSelectionViewHolder(
        private val itemBinding: BottomSheetItemBinding
    ) : BaseRecyclerViewHolder<User>(itemBinding) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: User) {
            itemBinding.tvMemberName.text =
                "${item.userDetails?.firstName} ${item.userDetails?.lastName}"
            itemBinding.clMember.setOnClickListener {
                onMemberClick(item)
            }
        }
    }
}