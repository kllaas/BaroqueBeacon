package com.example.baroquebeacon.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseUseCase<P, R>(
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: P): Result<R> {
        return withContext(ioDispatcher) {
            try {
                execute(params).let {
                    Result.success(it)
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    abstract suspend fun execute(params: P): R

}
