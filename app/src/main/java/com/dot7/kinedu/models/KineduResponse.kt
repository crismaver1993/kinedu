package com.dot7.kinedu.models

import com.dot7.kinedu.catalogue.activities.ActivityData

data class KineduResponse(var data: ActivityData, var meta: MetadataResponse)