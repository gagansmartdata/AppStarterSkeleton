package com.sdm.mediacard.utils.navigator

import android.os.Bundle
import androidx.navigation.NavController

class Navigator(override var navController: NavController) : NavigatorHelper {

    override fun navigate(resId: Int, bundle: Bundle?) {
        navController.navigate(resId, bundle)
    }

    override fun navigateBack(): Boolean {
        return navController.navigateUp()
    }
}