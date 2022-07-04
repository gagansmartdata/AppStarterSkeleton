package com.sdm.mediacard.presentation.flipit_anim_example

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.view.View
import androidx.core.animation.doOnEnd
import com.sdm.mediacard.R

class FlipHelper {

    fun flipCard(context: Context, visibleView: View, inVisibleView: View) {
        try {
            visibleView.visible()
            val scale = context.resources.displayMetrics.density
            val cameraDist = 8000 * scale
            visibleView.cameraDistance = cameraDist
            inVisibleView.cameraDistance = cameraDist
            val flipOutAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_out
                ) as AnimatorSet
            flipOutAnimatorSet.setTarget(inVisibleView)
            val flipInAnimatorSet =
                AnimatorInflater.loadAnimator(
                    context,
                    R.animator.flip_in
                ) as AnimatorSet
            flipInAnimatorSet.setTarget(visibleView)
            flipOutAnimatorSet.start()
            flipInAnimatorSet.start()

            flipInAnimatorSet.doOnEnd {
                inVisibleView.gone()
            }
        } catch (e: Exception) {
        }
    }

    fun View.visible(){
        this.visibility = View.VISIBLE
    }
    fun View.gone(){
        this.visibility = View.GONE
    }
}