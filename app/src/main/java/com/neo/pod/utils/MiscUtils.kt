package com.neo.pod.utils

import android.app.Activity
import android.util.DisplayMetrics


object MiscUtils {

    private val TAG = MiscUtils::class.simpleName

    fun getTimeString(millis: Long): String? {
        val builder = StringBuilder()
        val hours = (millis / (1000 * 60 * 60)).toInt()
        val minutes = (millis % (1000 * 60 * 60) / (1000 * 60)).toInt()
        val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        builder.append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds))
        return builder.toString()
    }

    fun getScreenW(activity: Activity): Int {
        val displayMetrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}