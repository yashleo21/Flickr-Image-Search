package com.yash2108.imagelookup.utils

import android.content.Context
import android.graphics.Point
import android.net.ConnectivityManager
import android.view.WindowManager

object Utils {

    fun getScreenWidth(context: Context): Int {
        val display =
            (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = Point()
        display.getSize(size)
        return size.x
    }

    fun isConnected(context: Context?): Boolean {
        if (context == null) return false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo == null) return false
        val netinfo = cm.activeNetworkInfo
        return if (netinfo != null) {
            if (netinfo.isConnectedOrConnecting) {
                val wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                val mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                mobile != null && mobile.isConnectedOrConnecting ||
                        wifi != null && wifi.isConnectedOrConnecting
            } else false
        } else false
    }
}