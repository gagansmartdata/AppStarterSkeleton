package com.sdei.base.network

sealed interface Resource<T> {
    class Success<T>(val data: T,val message: Any? = null) : Resource<T>
    //message is of Any type, it can be res int or string, so we can use them accordingly.
    class Error<T>(val message: Any,val data: T? = null) : Resource<T>
    class Loading<T>(val showLoader : Boolean = true) : Resource<T>
}