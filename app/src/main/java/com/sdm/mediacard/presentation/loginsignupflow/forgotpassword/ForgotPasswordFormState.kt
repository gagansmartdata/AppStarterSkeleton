package com.sdm.mediacard.presentation.loginsignupflow.forgotpassword

data class ForgotPasswordFormState(
    var email: String = "",
    var emailError: Any? = null,
    var showLoading: Boolean = false,
    var APIError: Any? = null,
    var requestSubmitted: Any? = null,
    var showSuccessDialog: Boolean = false
)