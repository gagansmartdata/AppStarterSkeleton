package com.sdei.domaindata.data.remote.apipayload

data class SignupPayload(
    val countryCode: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val mobile: String,
    val password: String,
    var roleId: String="123456789",
    val username: String
)