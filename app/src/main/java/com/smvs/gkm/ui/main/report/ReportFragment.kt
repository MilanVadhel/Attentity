package com.smvs.gkm.ui.main.report

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.smvs.gkm.R
import com.smvs.gkm.common.extensions.invisible
import com.smvs.gkm.common.extensions.safeObserve
import com.smvs.gkm.common.extensions.show
import com.smvs.gkm.common.extensions.showSnackBar
import com.smvs.gkm.common.utils.ProgressDialogUtil
import com.smvs.gkm.data.main.report.entity.Report
import com.smvs.gkm.databinding.FragmentReportBinding
import com.smvs.gkm.domain.base.UiState
import com.smvs.gkm.ui.common.base.BaseViewBindingFragment
import com.smvs.gkm.ui.main.report.adapter.ReportAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ReportFragment : BaseViewBindingFragment<FragmentReportBinding>(), View.OnClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private val reportAdapter: ReportAdapter by lazy {
        ReportAdapter(::handleCallToMember)
    }

    private fun handleCallToMember(contactNumber: String) {
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:${contactNumber}")
        startActivity(intent)
    }

    private val viewModel: ReportViewModel by viewModels()

    override fun getClassName(): String = ReportFragment::class.java.simpleName

    override fun inflateLayout(layoutInflater: LayoutInflater): FragmentReportBinding {
        return FragmentReportBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        super.initViews()
        setToolbarTitle(getString(R.string.text_title_report))
        initRecyclerView()
        initListeners()
        initApiCalls()
    }

    private fun initListeners() {
        binding.apply {
            ilReportHeader.ibBack.setOnClickListener(this@ReportFragment)
            slReports.setOnRefreshListener(this@ReportFragment)
        }
    }

    private fun initApiCalls() {
        viewModel.getReports()
    }

    private fun initRecyclerView() {
        binding.rvReport.adapter = reportAdapter
    }

    override fun initObservers() {
        super.initObservers()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.reportsLiveEvent.safeObserve(viewLifecycleOwner, ::handleReportsLiveEvent)
        }
    }

    private fun handleReportsLiveEvent(uiState: UiState<ArrayList<Report>>) {
        when (uiState) {
            is UiState.Loading -> {
                binding.slReports.isRefreshing = true
                ProgressDialogUtil.showProgressDialog(requireContext())
            }
            is UiState.Empty -> {
                ProgressDialogUtil.hideProgressDialog()
                binding.slReports.isRefreshing = false
                binding.apply {
                    rvReport.invisible()
                    tvNoReports.show()
                }
            }
            is UiState.Error -> {
                ProgressDialogUtil.hideProgressDialog()
                binding.slReports.isRefreshing = false
                view.showSnackBar(message = uiState.throwable.localizedMessage)
                binding.rvReport.invisible()
            }
            is UiState.Success -> {
                binding.slReports.isRefreshing = false
                ProgressDialogUtil.hideProgressDialog()
                reportAdapter.addAll(uiState.data, true)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.ibBack -> {
                findNavController().popBackStack()
            }
        }
    }

    override fun onRefresh() {
        initApiCalls()
    }
}