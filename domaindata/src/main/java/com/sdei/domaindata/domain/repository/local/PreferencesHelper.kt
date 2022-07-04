package com.sdei.domaindata.domain.repository.local

import kotlinx.coroutines.flow.Flow

/**
 * add all preferences related function here
 */
interface PreferencesHelper {
    fun getUserId(): Flow<String>
    suspend fun setUserId(id:String)
}