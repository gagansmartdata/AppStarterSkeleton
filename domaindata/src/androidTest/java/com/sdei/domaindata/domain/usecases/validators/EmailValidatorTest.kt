package com.sdei.domaindata.domain.usecases.validators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sdei.base.validation.ValidationResult
import com.sdei.domaindata.R
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmailValidatorTest {

    private lateinit var emailValidator: EmailValidator

    @Before
    fun beforeEach() {
        emailValidator = EmailValidator()
    }

    @Test
    fun validCase() {
        assertEquals(ValidationResult.Success, emailValidator.isValid("gagan@yopmail.com"))
    }

    @Test
    fun emptyCase() {
        assertEquals(ValidationResult.Failure(R.string.network_error), emailValidator.isValid(""))
    }

    @Test
    fun invalidFormatCase() {
        assertEquals(
            ValidationResult.Failure("Please enter a valid email"),
            emailValidator.isValid("gagan.com")
        )
    }

}