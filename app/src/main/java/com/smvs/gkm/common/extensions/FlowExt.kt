package com.smvs.gkm.common.extensions


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlin.experimental.ExperimentalTypeInference

@OptIn(ExperimentalTypeInference::class)
fun <T> Flow<Result<T>>.catch () : Flow<Result<T>> {
        return this.catch { e ->
                //emit(Result.Error(e))
        }
}