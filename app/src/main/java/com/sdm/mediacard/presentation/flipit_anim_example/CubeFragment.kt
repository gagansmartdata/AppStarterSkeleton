package com.sdm.mediacard.presentation.flipit_anim_example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.fragment.app.Fragment
import com.sdm.mediacard.R


class CubeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cube, container, false)
    }


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return CubeAnimation.create(CubeAnimation.RIGHT, enter, 700);
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            CubeFragment()
    }

}