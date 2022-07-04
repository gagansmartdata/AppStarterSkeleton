package com.sdm.mediacard.presentation.loginsignupflow.login

sealed class LoginScreenEvents{
    class EmailChanged(val value : String) : LoginScreenEvents()
    class PasswordChanged(val value : String) : LoginScreenEvents()
    object SubmitClicked : LoginScreenEvents()
}
