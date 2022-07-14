package com.sdei.domaindata.common

import com.sdei.base.ApiResponseWrapper
import com.sdei.base.network.Resource
import com.sdei.domaindata.R
import retrofit2.HttpException


fun <G> ApiResponseWrapper<G>.returnDataOrError(): Resource<G?> {
    return if(isSuccessful()) {
        Resource.Success<G?>(data, message)
    }
    else {
        message?.let {
            Resource.Error<G?>(it)
        } ?: Resource.Error<G?>(R.string.network_error)
    }
}


fun HttpException.getAPIErrorMsg(): String? {
/*    val moshi = Gson.Builder().build()
    val jsonAdapter: JsonAdapter<_ApiResponseWrapper> =
        moshi.adapter(_ApiResponseWrapper::class.java)*/
    return response()?.errorBody()?.string()
}
