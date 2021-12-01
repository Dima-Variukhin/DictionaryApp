package com.myapp.dictionaryapp.core

import java.lang.Exception

abstract class Abstract {
    interface Mapper<S, R> {
        fun map(data: S): R
    }
}

class NetworkConnectionException(cause: Throwable? = null) : Exception(cause)
class ServerUnavailableException : java.lang.Exception()