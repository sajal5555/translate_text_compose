package com.sonyassignment.webservice

/**
 * A generic class that holds a value or an exception
 */
sealed class RepoResult<out R>(val successData: R?) {

    data class Success<out T>(val value: T) : RepoResult<T>(value)
    data class Error(val errorMessage: String) : RepoResult<String>(errorMessage)

}

/**
 * `true` if [RepoResult] is of type [Success] & holds non-null [Success.data].
 */
val RepoResult<*>.succeeded
    get() = this is RepoResult.Success && successData != null

fun <T> RepoResult<T>.successOr(fallback: T): T {
    return (this as? RepoResult.Success<T>)?.successData ?: fallback
}
