package com.smvs.gkm.ui.common.base

import android.view.View
import android.widget.AdapterView

abstract class MySpinnerItemSelectionListener : AdapterView.OnItemSelectedListener {
    private var check = 0

    abstract fun onItemSelected(selectedItemPosition: Int)

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (++check > 1) {
            onItemSelected(position)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
}