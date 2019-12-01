package com.dot7.kinedu.catalogue.activities

data class ActivityData(var id:Long = 0,
                        var name:String = "",
                        var type:String = "",
                        var activities: MutableList<ActivityInfo>)