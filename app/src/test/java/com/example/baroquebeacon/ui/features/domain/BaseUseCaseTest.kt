package com.example.baroquebeacon.ui.features.domain

import com.example.baroquebeacon.domain.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class BaseUseCaseTest {

    private val dispatcher = Dispatchers.Unconfined

    @Test
    fun `invoke with successful execution should return Result_success`() = runTest {
        val useCase = object : BaseUseCase<String, Int>(dispatcher) {
            override suspend fun execute(params: String): Int {
                return 42
            }
        }

        val result = useCase.invoke("test")

        assertEquals(Result.success(42), result)
    }

    @Test
    fun `invoke with exception should return Result_failure`() = runTest {
        val useCase = object : BaseUseCase<String, Int>(dispatcher) {
            override suspend fun execute(params: String): Int {
                throw RuntimeException("Test Exception")
            }
        }

        val result = useCase.invoke("test")

        assertEquals(true, result.isFailure)
    }
}