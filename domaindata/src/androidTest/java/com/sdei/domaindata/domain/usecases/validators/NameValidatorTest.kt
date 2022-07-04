package com.sdei.domaindata.domain.usecases.validators

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.sdei.base.validation.ValidationResult
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NameValidatorTest {

    private lateinit var nameValidator: NameValidator

    @Before
    fun beforeEach() {
        nameValidator = NameValidator()
    }

    @Test
    fun validCase() {
        assertEquals(ValidationResult.Success, nameValidator.isValid("Name"))
    }

    @Test
    fun emptyCase() {
        assertEquals(ValidationResult.Failure("Please enter name"), nameValidator.isValid(""))
    }
}