package com.dot7.kinedu


import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.dot7.kinedu.network.KineduClient
import com.dot7.kinedu.network.KineduService
import com.dot7.kinedu.util.customview.FullScreenProgressDialog
import com.google.android.material.snackbar.Snackbar

/**
 * Contains all generic  variables & functions
 */
abstract class BaseFragment : Fragment() {
    var apiService: KineduService? = null
    private var mProgressDialog: FullScreenProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = KineduClient.getKineduNetworkClient()?.create(KineduService::class.java)
    }

    /**
     * Internet connectivity validation
     *
     * @param mContext current context
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
                    val display = activity?.windowManager?.defaultDisplay
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
        val isDestroyed = activity?.isDestroyed ?: false

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
        this@BaseFragment.context?.let {
            mProgressDialog = FullScreenProgressDialog(it, R.style.ProgressDialog)
            mProgressDialog?.setCancelable(false)
        }
    }

    /**
     * Generic snack bar
     *
     * @param mContext current context
     * @param view  view reference
     * @param textAction text to show in Snack Bar
     * @param msgText message text to show
     */
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