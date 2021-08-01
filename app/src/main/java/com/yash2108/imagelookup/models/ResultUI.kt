package com.yash2108.openissuesreader.models

import java.lang.Exception

sealed class ResultUI<out T> {

    class Loading : ResultUI<Nothing>()

    class Success<out T>(val data: T): ResultUI<T>()

    class Error(val exception: Exception): ResultUI<Nothing>()

    companion object {
        fun <T> success(data: T): ResultUI<T> = Success(data)
        fun error(exception: Exception): ResultUI<Nothing> = Error(exception)
        fun loading(): ResultUI<Nothing> = Loading()
    }
}
