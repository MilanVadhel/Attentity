package com.smvs.gkm.ui.main.report.adapter

import android.view.ViewGroup
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.toBinding
import com.smvs.gkm.data.main.report.entity.Report
import com.smvs.gkm.databinding.ItemReportBinding
import com.smvs.gkm.ui.common.base.BaseRecyclerViewAdapter
import com.smvs.gkm.ui.common.base.BaseRecyclerViewHolder

class ReportAdapter(
    val callToMember: (contactNumber: String) -> Unit
) : BaseRecyclerViewAdapter<Report, ReportAdapter.ReportViewHolder>() {

    override fun createItemViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder(parent.toBinding())
    }

    override fun bindItemViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ReportViewHolder(
        private val itemBinding: ItemReportBinding
    ) : BaseRecyclerViewHolder<Report>(itemBinding) {
        override fun bind(item: Report) {
            itemBinding.apply {
                tvAttendeeName.text = itemBinding.root.context.getString(
                    R.string.text_user_full_name,
                    item.firstName,
                    item.lastName
                )
                tvAttendeeMobileNumber.text = item.contact
                tvReportedBy.text =
                    itemBinding.root.context.getString(
                        R.string.text_reported_by,
                        item.assignedUser.firstName,
                        item.assignedUser.lastName
                    )
                tvAttendeeMobileNumber.setOnClickListener {
                    callToMember(item.contact)
                }
            }
        }
    }
}