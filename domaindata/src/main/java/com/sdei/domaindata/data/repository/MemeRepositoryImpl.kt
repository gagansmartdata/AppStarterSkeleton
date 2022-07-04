package com.sdei.domaindata.data.repository

import com.sdei.base.ApiResponseWrapper
import com.sdei.domaindata.data.remote.ApiInterface
import com.sdei.domaindata.domain.model.MemeData
import com.sdei.domaindata.domain.repository.MemeRepository
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val api: ApiInterface
) : MemeRepository {

    override suspend fun getMemeList(): ApiResponseWrapper<MemeData> {
        return with(api.getMemeList()){
            ApiResponseWrapper(data = data?.toMemeData(),statusCode = statusCode , message = message)
        }
    }
}
