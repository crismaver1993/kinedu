package com.dot7.kinedu.util.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView

class RectangleImageView : AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val desireheight = width * 2 / 3
        super.onMeasure(widthMeasureSpec, View.MeasureSpec.makeMeasureSpec(desireheight, View.MeasureSpec.EXACTLY))
    }

}
