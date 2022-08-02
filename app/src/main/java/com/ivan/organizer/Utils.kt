package com.ivan.organizer

import android.content.res.Resources

class Utils {
    fun Float.dp(): Float = this * density + 0.5f

    val density: Float
        get() = Resources.getSystem().displayMetrics.density
}