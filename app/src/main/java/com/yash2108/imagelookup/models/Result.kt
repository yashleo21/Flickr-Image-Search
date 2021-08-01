package com.yash2108.openissuesreader.models

import java.lang.Exception

sealed class Result<out T> {

    class Success<out T>(val data: T): Result<T>()

    class Error(val exception: Exception): Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> = Success(data)
        fun error(exception: Exception): Result<Nothing> = Error(exception)
    }
}
