package com.sdm.mediacard.presentation.loginsignupflow.forgotpassword

sealed class ForgotPasswordEvents{
    class EmailChanged(val value : String) : ForgotPasswordEvents()
    object SubmitClicked : ForgotPasswordEvents()
    class ShowHideDialog(val showDialog : Boolean) : ForgotPasswordEvents()
}
