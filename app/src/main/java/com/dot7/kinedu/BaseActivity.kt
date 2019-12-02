package com.dot7.kinedu

import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.dot7.kinedu.network.KineduClient
import com.dot7.kinedu.network.KineduService
import com.dot7.kinedu.util.customview.FullScreenProgressDialog
import com.google.android.material.snackbar.Snackbar

 abstract class BaseActivity : AppCompatActivity() {
    var apiService: KineduService? = null
    private var mProgressDialog: FullScreenProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = KineduClient.getKineduNetworkClient()?.create(KineduService::class.java)

    }

    /**
     * Internet connectivity validation
     */
    fun isOnline(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    /**
     * Show ProgressDialog
     */
    fun showProgressBar() {
        if (mProgressDialog == null) {
            createProgressBar()
        }
        try {
            mProgressDialog?.let { progressDialog ->
                if (!progressDialog.isShowing) {
                    progressDialog.show()
                    val display = windowManager?.defaultDisplay
                    val size = Point()
                    display?.getSize(size)
                    val width = size.x
                    val height = size.y
                    progressDialog.window?.setLayout(width, height)
                    progressDialog.setContentView(R.layout.view_loading)
                }
            }
        } catch (exception: Exception) {
            Log.e("BaseFragment", "${exception.message}")
        }
    }

    /**
     * Hide progressDialog
     */
    fun dismissProgressBar() {
        try {
            if (isDestroyed) {
                return
            }

            if (mProgressDialog == null) { //TODO Why this validation ? Check it
                createProgressBar()
            }

            mProgressDialog?.let { progressDialog ->
                if (progressDialog.isShowing) {
                    progressDialog.dismiss()
                }
            }
        } catch (e: Exception) {
            Log.e("BaseFragment", "$e")
        }
    }

    /**
     * Creación progressBar que muestra cuando se corre
     * proceso en background
     */
    private fun createProgressBar() {
        this@BaseActivity?.let {
            mProgressDialog = FullScreenProgressDialog(it, R.style.ProgressDialog)
            mProgressDialog?.setCancelable(false)
        }
    }

    fun showSnackBar(mContext: Context, view: View, textAction: String, msgText: String) {
        Snackbar.make(
            view,
            msgText,
            Snackbar.LENGTH_LONG
        )
            .setAction(textAction) { }
            .setActionTextColor(ContextCompat.getColor(mContext, R.color.colorAccent))
            .show()
    }
}
