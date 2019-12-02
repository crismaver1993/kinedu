package com.dot7.kinedu.util.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView

class SquareImageview : AppCompatImageView {
    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
