package com.dot7.kinedu.catalogue.activities

import com.google.gson.annotations.SerializedName

data class ActivityInfo(
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