package com.sdei.domaindata.domain.usecases.user

import com.sdei.base.network.Resource
import com.sdei.domaindata.R
import com.sdei.domaindata.common.getAPIErrorMsg
import com.sdei.domaindata.common.returnDataOrError
import com.sdei.domaindata.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ForgotPassword @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke(email: String): Flow<Resource<Any?>> =
    flow {
        try {
            emit(Resource.Loading())
            emit(repository.forgotPassword(email).returnDataOrError())
        } catch(e: HttpException) {
            e.getAPIErrorMsg()?.let {
                emit(Resource.Error(it))
            }   ?: R.string.network_error
        } catch(e: IOException) {
            emit(Resource.Error(R.string.network_error))
        }
    }
}