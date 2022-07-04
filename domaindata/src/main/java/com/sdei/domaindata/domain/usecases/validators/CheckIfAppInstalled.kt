package com.sdei.domaindata.domain.usecases.validators

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class CheckIfAppInstalled @Inject constructor(@ApplicationContext val appContext: Context) {

    operator fun invoke(packageName: String):  Boolean {
        val packageManager = appContext.packageManager
        return runCatching { packageManager.getPackageInfo(packageName, 0) }.isSuccess
    }
}