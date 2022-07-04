package com.sdm.mediacard.presentation.loginsignupflow.login

import com.sdei.domaindata.domain.model.UserData


data class LoginFormState(
    var email: String = "",
    var emailError: Any? = null,
    var password: String = "",
    var passwordError: Any? = null,
    var showLoading: Boolean = false,
    var APIError: Any? = null,
    var loginSuccessful: UserData? = null
)