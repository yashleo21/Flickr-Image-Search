package com.yash2108.imagelookup.utils

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

object Utils {

    fun getScreenWidth(context: Context): Int {
        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }
}