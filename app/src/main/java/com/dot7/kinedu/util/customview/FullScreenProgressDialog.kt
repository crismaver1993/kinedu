package com.dot7.kinedu.util.customview

import android.app.ProgressDialog
import android.content.Context
import android.view.WindowManager

/**
 *  Show progress dialog for background operations
 */
class FullScreenProgressDialog : ProgressDialog {
    constructor(context: Context) : super(context) {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
    }

    constructor(context: Context, theme: Int) : super(context, theme) {
        window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT)
    }
}