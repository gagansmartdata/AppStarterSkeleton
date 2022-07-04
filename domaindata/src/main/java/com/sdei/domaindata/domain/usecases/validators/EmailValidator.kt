package com.sdei.domaindata.domain.usecases.validators

import com.sdei.base.validation.DataValidator
import com.sdei.base.validation.ValidationResult
import com.sdei.domaindata.R


class EmailValidator : DataValidator<String> {

    override fun isValid(dataToValidate: String): ValidationResult {

        return when {
            dataToValidate.isEmpty() -> {
                ValidationResult.Failure(R.string.network_error)//pass string msg or resource id & handle them according to type
            }
            !android.util.Patterns.EMAIL_ADDRESS.matcher(dataToValidate)
                .matches()           -> {
                ValidationResult.Failure("Please enter a valid email")
            }
            else                     -> {
                ValidationResult.Success
            }
        }

    }
}