package com.dot7.kinedu.network

import com.dot7.kinedu.BuildConfig
import com.dot7.kinedu.util.KineduConstants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object KineduClient {
    private var retrofit: Retrofit? = null
    /**
     * Get current instance of Retrofit to start requesting to the server. Also
     * We check if Retrofit instance is null  or not
     *
     * @return Retrofit instance
     */
    fun getKineduNetworkClient(): Retrofit? {
        val httpClient = getHttpClient()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(KineduConstants.BASE_URL)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    /**
     * HttpClient builder to customize our client settings
     */
    private fun getHttpClient(): OkHttpClient.Builder {
        val httpClient =  OkHttpClient.Builder()
            .connectTimeout(KineduConstants.THIRTY_SECONDS, TimeUnit.SECONDS)
            .readTimeout(KineduConstants.THIRTY_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(KineduConstants.THIRTY_SECONDS, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.interceptors().add(logging)
        }
        return httpClient
    }
}