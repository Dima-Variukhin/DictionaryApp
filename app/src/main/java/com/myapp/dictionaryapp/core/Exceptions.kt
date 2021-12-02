package com.myapp.dictionaryapp.core

import java.lang.Exception

class NetworkConnectionException(cause: Throwable? = null) : Exception(cause)
class ServerUnavailableException : java.lang.Exception()