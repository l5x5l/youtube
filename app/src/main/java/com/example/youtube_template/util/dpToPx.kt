package com.example.youtube_template.util

import android.content.Context
import android.util.TypedValue

fun dpToPx(context: Context, dp : Int) : Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
}
