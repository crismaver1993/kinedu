package com.dot7.kinedu.interfaces

import com.dot7.kinedu.models.ActivityDataInfo
import com.dot7.kinedu.models.ArticleInfoData
import com.dot7.kinedu.util.customview.RectangleImageView

interface OnExerciseListener {

    fun showActivityDetail(activityInfo: ActivityDataInfo)

    fun showArticleDetail(activityInfo: ArticleInfoData,rectangleImageView: RectangleImageView)
}