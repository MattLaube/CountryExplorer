package com.laube.tech.countryexplorer.util

import android.graphics.Point
import android.view.View
import it.carlom.stikkyheader.core.animator.AnimatorBuilder
import it.carlom.stikkyheader.core.animator.HeaderStikkyAnimator

class IconAnimator(toAnimate: View?) : HeaderStikkyAnimator() {

    private val view:View?
    init{
        view = toAnimate
    }

    override fun getAnimatorBuilder(): AnimatorBuilder {
        val point = Point(50, 100) // translate to the point with coordinate (50,100);
        val scaleX = 1.5f //scale to the 50%
        val scaleY = 1.5f //scale to the 50%
        return AnimatorBuilder.create()
          .applyScale(view, scaleX, scaleY)
            //.applyTranslation(view, point)
    }
}