package com.sdei.domaindata.domain.usecases.validators

import com.sdei.base.validation.DataValidator
import com.sdei.base.validation.ValidationResult

class PasswordValidator : DataValidator<String> {

    override fun isValid(dataToValidate: String): ValidationResult {

        return when {
            dataToValidate.isEmpty()                                                   -> {
                ValidationResult.Failure("Please enter password")//pass string msg or resource id & handle them according to type
            }
            dataToValidate.length<6                                                    -> {
                ValidationResult.Failure("Password should be longer than 6 chars.")
            }
            !(dataToValidate.any(Char::isDigit) && dataToValidate.any(Char::isLetter)) -> {
                ValidationResult.Failure("Please enter a alphanumeric password.")
            }
            else                                                                       -> {
                ValidationResult.Success
            }
        }
    }
}