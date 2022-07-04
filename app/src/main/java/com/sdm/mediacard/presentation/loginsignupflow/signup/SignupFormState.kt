package com.sdm.mediacard.presentation.loginsignupflow.signup

import com.sdei.domaindata.domain.model.UserData


data class SignupFormState(
    var firstName: String = "",
    var firstNameError: Any? = null,
    var lastName: String = "",
    var lastNameError: Any? = null,
    var username: String = "",
    var usernameError: Any? = null,
    var countryCode: String = "",
    var countryCodeError: Any? = null,
    var mobile: String = "",
    var mobileError: Any? = null,
    var email: String = "",
    var emailError: Any? = null,
    var password: String = "",
    var passwordError: Any? = null,
    var repeatPassword: String = "",
    var repeatPasswordError: Any? = null,
    var showLoading: Boolean = false,
    var APIError: Any? = null,
    var loginSuccessful: UserData? = null
)