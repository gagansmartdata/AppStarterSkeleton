package com.sdei.domaindata.common

import com.sdei.base.ApiResponseWrapper
import com.sdei.base._ApiResponseWrapper
import com.sdei.base.network.Resource
import com.sdei.domaindata.R
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
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
    val moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<_ApiResponseWrapper> =
        moshi.adapter(_ApiResponseWrapper::class.java)
    return response()?.errorBody()?.string()?.let { jsonAdapter.fromJson(it)?.message }
}


/**
 * Represents all the chars used for nibble
 */
private const val CHARS = "0123456789abcdef"

/**
 * Encodes the given byte value as an hexadecimal character.
 */
fun encode(value: Byte): String {
    return CHARS[value.toInt().shr(4) and 0x0f].toString() + CHARS[value.toInt()
        .and(0x0f)].toString()
}

/**
 * Encodes the given byte array value to its hexadecimal representations, and prepends the given prefix to it.
 *
 * Note that by default the 0x prefix is prepended to the result of the conversion.
 * If you want to have the representation without the 0x prefix, pass to this method an empty prefix.
 */
fun encode(value: ByteArray, prefix: String = "0x"): String {
    return prefix + value.joinToString("") { encode(it) }
}

/**
 * Converts [this] [ByteArray] into its hexadecimal string representation prepending to it the given [prefix].
 *
 * Note that by default the 0x prefix is prepended to the result of the conversion.
 * If you want to have the representation without the 0x prefix,
 * pass to this method an empty [prefix].
 */
fun ByteArray.toHexString(prefix: String = "0x") = encode(this, prefix)
