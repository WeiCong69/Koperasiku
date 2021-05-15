package com.Koperasiku.decoration

import android.content.Context
import android.os.Handler
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

class SliderIndicator(
    context: Context,
    containerView: LinearLayout,
    viewPager: ViewPager,
    @DrawableRes drawableRes: Int
) : OnPageChangeListener {
    private val mContext: Context
    private val mContainer: LinearLayout?
    private var mDrawable: Int
    private var mSpacing = 0
    private var mSize = 0
    private val mViewPager: ViewPager
    private var mPageCount = 0
    private var mInitialPage = 0
    private val defaultSizeInDp = 12
    private val defaultSpacingInDp = 12
    fun setPageCount(pageCount: Int) {
        mPageCount = pageCount
    }

    fun setInitialPage(page: Int) {
        mInitialPage = page
    }

    fun setDrawable(@DrawableRes drawable: Int) {
        mDrawable = drawable
    }

    fun setSpacingRes(@DimenRes spacingRes: Int) {
        mSpacing = spacingRes
    }

    fun setSize(@DimenRes dimenRes: Int) {
        mSize = dimenRes
    }

    fun show() {
        initIndicators()
        setIndicatorAsSelected(mInitialPage)
        Handler().postDelayed({ mViewPager.currentItem = 1 }, 2500)
    }

    private fun initIndicators() {
        if (mContainer == null || mPageCount <= 0) {
            return
        }
        mViewPager.addOnPageChangeListener(this)
        val res = mContext.resources
        mContainer.removeAllViews()
        for (i in 0 until mPageCount) {
            val view = View(mContext)
            val dimen =
                if (mSize != 0) res.getDimensionPixelSize(mSize) else res.displayMetrics.density.toInt() * defaultSizeInDp
            val margin =
                if (mSpacing != 0) res.getDimensionPixelSize(mSpacing) else res.displayMetrics.density.toInt() * defaultSpacingInDp
            val lp = LinearLayout.LayoutParams(dimen, dimen)
            lp.setMargins(if (i == 0) 0 else margin, 0, 0, 0)
            view.layoutParams = lp
            view.setBackgroundResource(mDrawable)
            view.isSelected = i == 0
            mContainer.addView(view)
        }
    }

    private fun setIndicatorAsSelected(index: Int) {
        if (mContainer == null) {
            return
        }
        for (i in 0 until mContainer.childCount) {
            val view = mContainer.getChildAt(i)
            view.isSelected = i == index
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
    override fun onPageSelected(position: Int) {
        val index = position % mPageCount
        setIndicatorAsSelected(index)
        val moveTo = position + 1
        try {
            Handler().postDelayed({ mViewPager.currentItem = moveTo }, 2500)
        } catch (e: Exception) {
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
    fun cleanup() {
        mViewPager.clearOnPageChangeListeners()
    }

    init {
        requireNotNull(context) { "context cannot be null" }
        requireNotNull(containerView) { "containerView cannot be null" }
        requireNotNull(viewPager) { "ViewPager cannot be null" }
        requireNotNull(viewPager.adapter) { "ViewPager does not have an adapter set on it." }
        mContext = context
        mContainer = containerView
        mDrawable = drawableRes
        mViewPager = viewPager
    }
}