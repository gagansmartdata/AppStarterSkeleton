package com.sdei.domaindata.data.repository

import com.sdei.base.ApiResponseWrapper
import com.sdei.domaindata.data.remote.ApiInterface
import com.sdei.domaindata.data.remote.apipayload.SignupPayload
import com.sdei.domaindata.domain.model.UserData
import com.sdei.domaindata.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : UserRepository {

    override suspend fun loginUser(
        email: String,
        password: String,
        isTerminateExistingSession: Boolean
    ): ApiResponseWrapper<UserData> {
        return with(api.login(email, password, isTerminateExistingSession)) {
            ApiResponseWrapper<UserData>(data = data?.toUserData(),statusCode = statusCode , message = message)
        }
    }

    override suspend fun signupUser(signupPayload: SignupPayload): ApiResponseWrapper<UserData> {
        return with(api.signup(signupPayload)){
            ApiResponseWrapper<UserData>(data = data?.toUserData(),statusCode = statusCode , message = message)
        }
    }

    override suspend fun forgotPassword(email: String): ApiResponseWrapper<Any> {
        return with(api.forgotPassword(email)){
            ApiResponseWrapper<Any>(data = data,statusCode = statusCode , message = message)
        }
    }
}
