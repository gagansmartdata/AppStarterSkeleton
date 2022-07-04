package com.sdei.domaindata.domain.usecases.validators

import com.sdei.base.validation.DataValidator
import com.sdei.base.validation.ValidationResult


class NameValidator : DataValidator<String> {

    override fun isValid(dataToValidate: String): ValidationResult {

        return when {
            dataToValidate.isEmpty() -> {
                ValidationResult.Failure("Please enter name")//pass string msg or resource id & handle them according to type
            }
            dataToValidate.length<2  -> {
                ValidationResult.Failure("Please enter a valid name")
            }
            else                     -> {
                ValidationResult.Success
            }
        }
    }
}