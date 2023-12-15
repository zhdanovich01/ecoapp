package com.example.neweco.logic

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout
import com.example.neweco.MainActivity
import com.example.neweco.R

class ExpandableView(context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private val gestureDetector: GestureDetector
    private var isExpanded = false
    private val defaultHeight: Int
    private val expandedHeight: Int
    private val maxHeight: Int

    init {
        gestureDetector = GestureDetector(context, GestureListener())
        defaultHeight = resources.getDimensionPixelSize(R.dimen.default_height)
        expandedHeight = resources.getDimensionPixelSize(R.dimen.expanded_height)

        // Установим maxHeight в высоту экрана с учетом отступа 50dp
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val size = android.graphics.Point()
        display.getSize(size)
        val screenHeight = size.y
        val marginTop = resources.getDimensionPixelSize(R.dimen.margin_top)
        maxHeight = screenHeight - marginTop

        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, defaultHeight)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                // Начало касания на первом фрейме
                // Мы не знаем, будет ли это свайп, поэтому просто устанавливаем максимальную высоту

                (context as? MainActivity)?.makebackdarker()
                (context as? MainActivity)?.makemygeoinvis()
                updateHeight(maxHeight)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                // Завершение касания на первом фрейме
                if (!isExpanded) {
                    // Если не свернут, восстанавливаем высоту
                    (context as? MainActivity)?.makeliteagain()
                    (context as? MainActivity)?.makemygeovis()
                    updateHeight(expandedHeight)
                }
            }
        }
        return true
    }

    private inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (Math.abs(velocityY) > Math.abs(velocityX)) {
                if (isExpanded && velocityY > 0) {
                    collapse()
                } else if (!isExpanded && velocityY < 0) {
                    expand()
                }
                return true
            }
            return false
        }
    }

    fun expand() {
        isExpanded = true
        (context as? MainActivity)?.makeliteagain()
        (context as? MainActivity)?.makemygeovis()
        updateHeight(expandedHeight)
    }

    fun collapse() {
        isExpanded = false
        (context as? MainActivity)?.makeliteagain()
        (context as? MainActivity)?.makemygeoinvis()
        updateHeight(defaultHeight)
    }

    private fun updateHeight(targetHeight: Int) {
        val finalHeight = targetHeight.coerceIn(defaultHeight, maxHeight)
        val animator = createHeightAnimator(finalHeight)
        animator.start()
    }

    private fun createHeightAnimator(targetHeight: Int): ValueAnimator {
        val animator = ValueAnimator.ofInt(height, targetHeight)
        animator.addUpdateListener { valueAnimator ->
            layoutParams.height = valueAnimator.animatedValue as Int
            requestLayout()
        }
        animator.duration = 500
        return animator
    }
}
