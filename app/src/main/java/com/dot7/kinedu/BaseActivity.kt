package com.dot7.kinedu

import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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

     /**
      * String converter to HTML
      */
     fun String.toSpannedText(): Spanned {
         return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
             Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
         } else {
             Html.fromHtml(this)
         }
     }

     /**
      * Show Internet error
      * @param errorMessage id string erro to show
      * @param actionName id string action text
      * @param listener interface to do action
      */
     fun showSnackError(errorMessage: Int, actionName: Int, listener: View.OnClickListener) {
         val snackbar = Snackbar.make(
             findViewById(android.R.id.content),
             errorMessage,
             Snackbar.LENGTH_INDEFINITE
         )
         snackbar.setAction(actionName, listener)
         snackbar.view.setBackgroundColor(ContextCompat.getColor(baseContext, R.color.colorRed))
         val button =
             snackbar.view.findViewById<Button>(com.google.android.material.R.id.snackbar_action)
         button.transformationMethod = null
         snackbar.setActionTextColor(ContextCompat.getColor(baseContext, android.R.color.white))
         (snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView).maxLines = 5
         snackbar.show()
     }
}
