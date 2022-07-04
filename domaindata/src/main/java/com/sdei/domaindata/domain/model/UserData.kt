package com.sdei.domaindata.domain.model

data class UserData(
    val accountVerificationToken: String,
    val email: String,
    val firstName: String,
    val id: String,
    val isAccountLocked: Boolean,
    val isEmailVerified: Boolean,
    val lastName: String,
    val mobile: String
)