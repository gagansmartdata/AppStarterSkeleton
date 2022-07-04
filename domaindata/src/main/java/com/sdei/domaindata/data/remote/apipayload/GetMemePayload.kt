package com.sdei.domaindata.data.remote.apipayload

data class GetMemePayload(
    val limit: Int,
    val page: Int,
    val search: String,
    val searchBy: String,
    val sortBy: String,
    val sortOrder: String
)