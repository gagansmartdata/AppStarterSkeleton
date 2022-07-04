package com.sdei.domaindata.domain.repository

import com.sdei.base.ApiResponseWrapper
import com.sdei.domaindata.domain.model.MemeData

interface MemeRepository {
    suspend fun getMemeList(
    ): ApiResponseWrapper<MemeData>
}
