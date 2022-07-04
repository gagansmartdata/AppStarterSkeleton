/*
package com.sdm.mediacard.presentation

import android.content.Context
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.google.android.filament.utils.KTXLoader
import com.sdei.base.BaseActivity
import com.sdm.mediacard.R
import com.sdm.mediacard.databinding.ActivitySceneViewBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.sceneview.defaultIbl
import io.github.sceneview.environment.loadEnvironment
import io.github.sceneview.math.Position
import io.github.sceneview.math.Rotation
import io.github.sceneview.node.ModelNode
import kotlinx.coroutines.delay


@AndroidEntryPoint
class SceneViewActivity : BaseActivity<ActivitySceneViewBinding>() {

    override val layoutId: Int
        get() = R.layout.activity_scene_view
    override var binding: ActivitySceneViewBinding
        get() = setUpBinding()
        set(value) {}


    override fun onCreate() {
        binding.sceneView.camera.position = Position(x = 4.0f, y = -1.0f)
        binding.sceneView.camera.rotation = Rotation(x = 0.0f, y = 80.0f)


        val modelNode = ModelNode(position = Position(z = -4.0f))
        binding.sceneView.addChild(modelNode)

        lifecycleScope.launchWhenCreated {
            binding.sceneView.environment = KTXLoader.loadEnvironment(
                this@SceneViewActivity,
                lifecycle = lifecycle,
                iblKtxFileLocation = defaultIbl
                //,skyboxKtxFileLocation = defaultSkybox
            )

            modelNode.loadModel(
                context = this@SceneViewActivity,
                lifecycle = lifecycle,
                glbFileLocation = "models/glbfile.glb",
                autoAnimate = true,
                autoScale = true,
                centerOrigin = Position(x = 0.0f, y = 0.0f, z = 0.0f)
            )


            // We currently have an issue while the model render is not completely loaded
            delay(200)
            binding.sceneView.camera.smooth(
                position = Position(x = -1.0f, y = 1.5f, z = -3.5f),
                rotation = Rotation(x = -60.0f, y = -50.0f),
                speed = 0.5f
            )
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, SceneViewActivity::class.java))
        }
    }

}*/
