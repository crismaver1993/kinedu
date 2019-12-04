package com.dot7.kinedu

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.dot7.kinedu.catalogue.CatalogueActivity

class SplashActivity : BaseActivity() {
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        mDelayHandler = Handler()
        mDelayHandler?.postDelayed(mRunnable, SPLASH_DELAY)
    }

    private val mRunnable: Runnable = Runnable {
        if (!isFinishing) {
            val intent = Intent(applicationContext, CatalogueActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    public override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler?.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}