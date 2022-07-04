package com.sdei.domaindata.domain.usecases.user

import com.sdei.base.network.Resource
import com.sdei.domaindata.R
import com.sdei.domaindata.common.getAPIErrorMsg
import com.sdei.domaindata.common.returnDataOrError
import com.sdei.domaindata.data.remote.apipayload.SignupPayload
import com.sdei.domaindata.domain.model.UserData
import com.sdei.domaindata.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserSignup @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(signupPayload: SignupPayload): Flow<Resource<UserData?>> =
    flow {
        try {
            emit(Resource.Loading<UserData?>())
            emit(repository.signupUser(signupPayload).returnDataOrError())
        } catch(e: HttpException) {
            e.getAPIErrorMsg()?.let {
                emit(Resource.Error<UserData?>(it))
            }   ?: R.string.network_error
        } catch(e: IOException) {
            emit(Resource.Error<UserData?>(R.string.network_error))
        }
    }
}