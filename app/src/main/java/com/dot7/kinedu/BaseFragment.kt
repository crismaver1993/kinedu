package com.dot7.kinedu


import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.dot7.kinedu.network.KineduClient
import com.dot7.kinedu.network.KineduService

/**
 * Contains all generic  variables & functions
 */
abstract class BaseFragment : Fragment() {
    var apiService: KineduService? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apiService = KineduClient.getKineduNetworkClient()?.create(KineduService::class.java)
    }

     fun isOnline(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}