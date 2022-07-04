package com.sdm.mediacard.presentation.flipit_anim_example

import android.view.animation.Transformation
import androidx.annotation.IntDef

open class CubeAnimation private constructor(
    @field:Direction @param:Direction protected val mDirection: Int,
    protected val mEnter: Boolean,
    duration: Long
) : ViewPropertyAnimation() {
    @IntDef(UP, DOWN, LEFT, RIGHT)
    @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
    internal annotation class Direction
    private class VerticalCubeAnimation(@Direction direction: Int, enter: Boolean, duration: Long) :
        CubeAnimation(direction, enter, duration) {
        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            mPivotX = width * 0.5f
            mPivotY = if (mEnter == (mDirection == UP)) 0.0f else height.toFloat()
            mCameraZ = -height * 0.015f
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            var value = if (mEnter) interpolatedTime - 1.0f else interpolatedTime
            if (mDirection == DOWN) value *= -1.0f
            mRotationX = value * 90.0f
            mTranslationY = -value * mHeight
            super.applyTransformation(interpolatedTime, t)
            applyTransformation(t)
        }
    }

    private class HorizontalCubeAnimation(
        @Direction direction: Int,
        enter: Boolean,
        duration: Long
    ) : CubeAnimation(direction, enter, duration) {
        override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
            super.initialize(width, height, parentWidth, parentHeight)
            mPivotX = if (mEnter == (mDirection == LEFT)) 0.0f else width.toFloat()
            mPivotY = height * 0.5f
            mCameraZ = -width * 0.015f
        }

        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            var value = if (mEnter) interpolatedTime - 1.0f else interpolatedTime
            if (mDirection == RIGHT) value *= -1.0f
            mRotationY = -value * 90.0f
            mTranslationX = -value * mWidth
            super.applyTransformation(interpolatedTime, t)
            applyTransformation(t)
        }
    }

    companion object {
        const val UP = 1
        const val DOWN = 2
        const val LEFT = 3
        const val RIGHT = 4

        /**
         * Create new Animation.
         * @param direction Direction of animation
         * @param enter true for Enter / false for Exit
         * @param duration Duration of Animation
         * @return
         */
        fun create(@Direction direction: Int, enter: Boolean, duration: Long): CubeAnimation {
            return when (direction) {
                UP, DOWN -> VerticalCubeAnimation(
                    direction,
                    enter,
                    duration
                )
                LEFT, RIGHT -> HorizontalCubeAnimation(
                    direction,
                    enter,
                    duration
                )
                else -> HorizontalCubeAnimation(direction, enter, duration)
            }
        }
    }

    init {
        setDuration(duration)
    }
}