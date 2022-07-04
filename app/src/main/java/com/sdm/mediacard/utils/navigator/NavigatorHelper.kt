package com.sdm.mediacard.utils.navigator

import android.os.Bundle
import androidx.navigation.NavController

interface NavigatorHelper {

    var navController: NavController

    fun navigate(resId: Int, bundle: Bundle? = null)

    fun navigateBack() : Boolean
}