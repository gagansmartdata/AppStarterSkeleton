package com.sdei.base

/**
 * Base data class for UI state
 */
data class ApiResponseWrapper<G>(
    val data: G? = null,
    val statusCode: Int? = null,
    val message: String? = null
){
    fun isSuccessful() = statusCode in 200..299
}


data class _ApiResponseWrapper(
    val data: Any? = null,
    val statusCode: Int? = null,
    val message: String? = null
)

