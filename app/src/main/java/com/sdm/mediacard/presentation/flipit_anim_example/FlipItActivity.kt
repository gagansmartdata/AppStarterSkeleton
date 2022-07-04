package com.sdm.mediacard.presentation.flipit_anim_example

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentTransaction
import com.sdm.mediacard.R
import com.sdei.base.BaseActivity
import com.sdm.mediacard.databinding.ActivityFlipItBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FlipItActivity : BaseActivity<ActivityFlipItBinding>()
    {

    override val layoutId: Int
        get() = R.layout.activity_flip_it
    override var binding: ActivityFlipItBinding
        get() = setUpBinding()
        set(value) {}

        override fun onCreate() {

        binding.btnFlip.setOnClickListener {
            FlipHelper().flipCard(this, binding.waterProgressViewFlip,binding.waterProgressView)
        }
        binding.btnFlipBack.setOnClickListener {
            FlipHelper().flipCard(this, binding.waterProgressView,binding.waterProgressViewFlip)
        }
        binding.btnCube.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            ft.replace(R.id.layoutCube, CubeFragment.newInstance())
            ft.commit()
        }
    }

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, FlipItActivity::class.java))
        }
    }
}