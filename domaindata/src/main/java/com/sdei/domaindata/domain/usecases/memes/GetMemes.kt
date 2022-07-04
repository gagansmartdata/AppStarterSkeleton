package com.sdei.domaindata.domain.usecases.memes

import com.sdei.base.network.Resource
import com.sdei.domaindata.R
import com.sdei.domaindata.common.getAPIErrorMsg
import com.sdei.domaindata.common.returnDataOrError
import com.sdei.domaindata.domain.model.MemeData
import com.sdei.domaindata.domain.repository.MemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMemes @Inject constructor(
    private val repository: MemeRepository
) {
    operator fun invoke(): Flow<Resource<MemeData?>> =
    flow {
        try {
            emit(Resource.Loading())
            emit(repository.getMemeList().returnDataOrError())
        } catch(e: HttpException) {
            e.getAPIErrorMsg()?.let {
                emit(Resource.Error(it))
            }   ?: R.string.network_error
        } catch(e: IOException) {
            emit(Resource.Error(R.string.network_error))
        }
    }
}