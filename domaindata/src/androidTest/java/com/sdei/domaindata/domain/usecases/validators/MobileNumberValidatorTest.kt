package com.sdei.domaindata.domain.usecases.validators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sdei.base.validation.ValidationResult
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MobileNumberValidatorTest {

    private lateinit var mobileNumberValidator: MobileNumberValidator

    @Before
    fun beforeEach() {
        mobileNumberValidator = MobileNumberValidator()
    }

    @Test
    fun validCase() {
        assertEquals(ValidationResult.Success, mobileNumberValidator.isValid("gagan@gmail.com"))
    }

    @Test
    fun emptyCase() {
        assertEquals(
            ValidationResult.Failure("Please enter mobile number."),
            mobileNumberValidator.isValid("")
        )
    }

    @Test
    fun invalidFormatCase() {
        assertEquals(
            ValidationResult.Failure("Please enter a valid mobile number."),
            mobileNumberValidator.isValid("123456")
        )
    }

}