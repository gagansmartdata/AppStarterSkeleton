package com.sdm.mediacard.presentation.loginsignupflow.signup

sealed class SignupScreenEvents{
    class FirstNameChanged(val value : String) : SignupScreenEvents()
    class LastNameChanged(val value : String) : SignupScreenEvents()
    class UserNameChanged(val value : String) : SignupScreenEvents()
    class EmailChanged(val value : String) : SignupScreenEvents()
    class CountryCodeChanged(val value : String) : SignupScreenEvents()
    class MobileChanged(val value : String) : SignupScreenEvents()
    class PasswordChanged(val value : String) : SignupScreenEvents()
    class RePasswordChanged(val value : String) : SignupScreenEvents()
    object SubmitClicked : SignupScreenEvents()
}
