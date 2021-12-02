package com.myapp.dictionaryapp.core

abstract class Abstract {
    interface Mapper<S, R> {
        fun map(data: S): R
    }
}
