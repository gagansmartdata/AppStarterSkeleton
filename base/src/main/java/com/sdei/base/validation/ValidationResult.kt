package com.sdei.base.validation

sealed class ValidationResult {
    object Success : ValidationResult()
    data class Failure(val errorMsg: Any) : ValidationResult()//error msg can be string or int res
}