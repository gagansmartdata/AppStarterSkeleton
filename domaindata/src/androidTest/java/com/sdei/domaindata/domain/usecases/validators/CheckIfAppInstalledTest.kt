package com.sdei.domaindata.domain.usecases.validators

import android.content.Context
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sdei.domaindata.test.BuildConfig
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class CheckIfAppInstalledTest {

    lateinit var checkIfAppInstalled : CheckIfAppInstalled

    @Before
    fun beforeEach() {
        val appContext: Context = InstrumentationRegistry.getInstrumentation().getTargetContext()
        checkIfAppInstalled = CheckIfAppInstalled(appContext)
    }

    @Test
    fun trueCase_checkIfAppInstalled() {
        TestCase.assertEquals(checkIfAppInstalled(BuildConfig.APPLICATION_ID), true)
    }
    @Test
    fun falseCase_checkIfAppInstalled() {
        TestCase.assertEquals(checkIfAppInstalled("com.sdm.wrongpackage"), true)
    }

}