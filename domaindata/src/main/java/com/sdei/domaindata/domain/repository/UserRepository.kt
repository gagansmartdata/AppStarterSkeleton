package com.sdei.domaindata.domain.repository

import com.sdei.base.ApiResponseWrapper
import com.sdei.domaindata.data.remote.apipayload.SignupPayload
import com.sdei.domaindata.domain.model.UserData

interface UserRepository {

    suspend fun loginUser(
        email: String,
        password: String,
        isTerminateExistingSession: Boolean
    ): ApiResponseWrapper<UserData>

    suspend fun signupUser(signupPayload: SignupPayload): ApiResponseWrapper<UserData>


    suspend fun forgotPassword(
        email: String
    ): ApiResponseWrapper<Any>
}
