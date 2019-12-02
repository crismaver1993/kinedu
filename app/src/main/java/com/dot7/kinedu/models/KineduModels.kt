package com.dot7.kinedu.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

// ========================================================================================
//                                  Generic
// ========================================================================================
data class MetadataResponse(
    var page: Int = 0,
    var perPage: Int = 0,
    var totalPage: Int = 0,
    var total: Int = 0
)

// ========================================================================================
//                                  Activities
// ========================================================================================
data class KineduActivityResponse(var data: ActivityData, var meta: MetadataResponse)

data class ActivityData(
    var id: Long = 0,
    var name: String = "",
    var type: String = "",
    var activities: MutableList<ActivityDataInfo>
)

data class ActivityDataInfo(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("age")
    var age: String = "",
    @SerializedName("age_group")
    var ageGroup: String = "",
    @SerializedName("activity_type")
    var activityType: String = "",
    @SerializedName("is_holiday")
    var isHoliday: Boolean = false,
    @SerializedName("domain_id")
    var domainId: Int = 0,
    @SerializedName("area_id")
    var areaId: Int = 0,
    @SerializedName("purpose")
    var purpose: String = "",
    @SerializedName("thumbnail")
    var thumbnail: String = "",
    @SerializedName("active_milestones")
    var activeMilestones: Int = 0,
    @SerializedName("completed_milestones")
    var completeMilestones: Int = 0,
    @SerializedName("dap_lifes_checked")
    var dapLifesChecked: Boolean = false
)

// ========================================================================================
//                                  Articles
// ========================================================================================

data class KineduArticleResponse(var data: ArticleData, var meta: MetadataResponse)
data class ArticleData(var articles: MutableList<ArticleInfoData>)
data class KineduArticleDetailResponse(var data: KineduArticleDetail)

data class ArticleInfoData(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("type")
    var type: String = "",
    @SerializedName("min_age")
    var minAge: String = "",
    @SerializedName("max_age")
    var maxAge: String = "",
    @SerializedName("picture")
    var picture: String = "",
    @SerializedName("area_id")
    var areaId: String = "",
    @SerializedName("short_description")
    var shortDesc: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(minAge)
        parcel.writeString(maxAge)
        parcel.writeString(picture)
        parcel.writeString(areaId)
        parcel.writeString(shortDesc)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ArticleInfoData> {
        override fun createFromParcel(parcel: Parcel): ArticleInfoData {
            return ArticleInfoData(parcel)
        }

        override fun newArray(size: Int): Array<ArticleInfoData?> {
            return arrayOfNulls(size)
        }
    }
}

data class KineduArticleDetail(
    @SerializedName("article")
    var article: ArticleDetailinfo,
    @SerializedName("related_items")
    var relatedItem: ArticleDeatilRelatedData
)

data class ArticleDeatilRelatedData(
    var activities: MutableList<ArticleActivityRelatedInfo>,
    var articles: MutableList<ArticleDetailRelatedInfo>
)

data class ArticleDetailinfo(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("picture")
    var picture: String = "",
    @SerializedName("link")
    var link: String = "",
    @SerializedName("area_id")
    var areaId: Int = 0,
    @SerializedName("body")
    var body: String = "",
    @SerializedName("faved")
    var faved: Boolean = false
)

data class ArticleDetailRelatedInfo(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("title")
    var title: String = "",
    @SerializedName("picture")
    var picture: String = "",
    @SerializedName("link")
    var link: String = "",
    @SerializedName("area_id")
    var areaId: Int = 0,
    @SerializedName("short_description")
    var shortDesc: String = "",
    @SerializedName("faved")
    var faved: Boolean = false
)

data class ArticleActivityRelatedInfo(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("description")
    var description: String = "",
    @SerializedName("purpose")
    var purpose: String = "",
    @SerializedName("age")
    var age: Int = 0,
    @SerializedName("age_group")
    var ageGroup: String = "",
    @SerializedName("activity_type")
    var activityType: String = "",
    @SerializedName("video_provider_thumbnails")
    var videoProviderThumbnails: String = "",
    @SerializedName("video_id")
    var videoId: String = "",
    @SerializedName("area_id")
    var areaId: String = "",
    @SerializedName("shareable_video_url")
    var shareable_video_url: String = "",
    @SerializedName("shareable_thumbnail_url")
    var shareableThumbnailUrl: String = "",
    @SerializedName("locked")
    var locked: Boolean = false,
    @SerializedName("picture")
    var picture: String = "",
    @SerializedName("faved")
    var faved: Boolean = false
)