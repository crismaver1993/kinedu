package com.dot7.kinedu.catalogue.activities

import com.dot7.kinedu.models.ActivityDataInfo

/**
 *  Activities State
 */
sealed class ActivitiesState {
    object ShowInternetAlertRetry : ActivitiesState()
    class ShowActivities(var activities: MutableList<ActivityDataInfo>) : ActivitiesState()
}