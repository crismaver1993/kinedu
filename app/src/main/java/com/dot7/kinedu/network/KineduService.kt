package com.dot7.kinedu.network

import com.dot7.kinedu.models.KineduActivityResponse
import com.dot7.kinedu.models.KineduArticleDetailResponse
import com.dot7.kinedu.models.KineduArticleResponse
import retrofit2.Call
import retrofit2.http.*

interface KineduService {

    // ========================================================================================
    //                                  Activities
    // ========================================================================================

    @Headers("Content-Type: application/json")
    @GET("catalogue/activities")
    fun getActivities(
        @Header("Authorization") authorization: String,
        @Query("skill_id") skillId: String,
        @Query("baby_id") babyId: String
    ): Call<KineduActivityResponse>

    // ========================================================================================
    //                                  Articles
    // ========================================================================================

    @Headers("Content-Type: application/json")
    @GET("catalogue/articles")
    fun getArticles(
        @Header("Authorization") authorization: String,
        @Query("skill_id") skillId: String,
        @Query("baby_id") babyId: String
    ): Call<KineduArticleResponse>

    @Headers("Content-Type: application/json")
    @GET("articles/{article_id}")
    fun getArticleDetail(
        @Header("Authorization") authorization: String,
        @Path("article_id") articleId: String
    ): Call<KineduArticleDetailResponse>
}