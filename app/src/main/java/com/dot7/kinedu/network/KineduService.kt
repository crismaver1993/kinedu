package com.dot7.kinedu.network

import com.dot7.kinedu.models.KineduResponse
import com.dot7.kinedu.models.MetadataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface KineduService {
    /**
     * Activities
     */
    @Headers("Content-Type: application/json")
    @GET("catalogue/activities")
    fun getActivities(
        @Header("Authorization") authorization: String,
        @Query("skill_id") skillId: String,
        @Query("babyId") babyId: String
    ): Call<KineduResponse>

    /**
     * Articles
     */
    @Headers("Content-Type: application/json")
    @GET("catalogue/articles")
    fun getArticles(
        @Header("Authorization") authorization: String,
        @Query("skill_id") skillId: String,
        @Query("babyId") babyId: String
    ): Call<KineduResponse>

    @Headers("Content-Type: application/json")
    @GET("articles/{article_id}")
    fun getArticleDetail(
        @Header("Authorization") authorization: String,
        @Query("article_id") articleId: String
    ): Call<KineduResponse>
}