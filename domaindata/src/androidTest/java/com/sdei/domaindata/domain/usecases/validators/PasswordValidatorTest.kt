package com.sdei.domaindata.domain.usecases.validators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sdei.base.validation.ValidationResult
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PasswordValidatorTest {

    private lateinit var passwordValidator: PasswordValidator

    @Before
    fun beforeEach() {
        passwordValidator = PasswordValidator()
    }

    @Test
    fun validCase() {
        assertEquals(ValidationResult.Success, passwordValidator.isValid("password123"))
    }

    @Test
    fun emptyCase() {
        assertEquals(ValidationResult.Failure("Please enter password"), passwordValidator.isValid(""))
    }
    @Test
    fun shortPasswordCase() {
        assertEquals(ValidationResult.Failure("Password should be longer than 6 chars."), passwordValidator.isValid("12345"))
    }

    @Test
    fun invalidCase() {
        assertEquals(ValidationResult.Failure("Please enter a alphanumeric password."), passwordValidator.isValid("123456789"))
    }
}