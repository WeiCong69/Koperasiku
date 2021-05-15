package com.Koperasiku.decoration

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.animation.DecelerateInterpolator
import android.widget.Scroller
import androidx.viewpager.widget.ViewPager

class BannerSlider : ViewPager {
    //public static final int SLIDE_MODE_SCROLL_DURATION = 1000;
    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun init() {
        setDurationScroll(DEFAULT_SCROLL_DURATION)
        setOnTouchListener { v, event -> true }
    }

    fun setDurationScroll(millis: Int) {
        try {
            val viewpager: Class<*> = ViewPager::class.java
            val scroller = viewpager.getDeclaredField("mScroller")
            scroller.isAccessible = true
            scroller[this] = OwnScroller(context, millis)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    inner class OwnScroller(context: Context?, private val durationScrollMillis: Int) :
        Scroller(context, DecelerateInterpolator()) {
        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, durationScrollMillis)
        }
    }

    companion object {
        const val DEFAULT_SCROLL_DURATION = 200
    }
}