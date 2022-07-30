package com.smvs.gkm.di.module

import com.smvs.gkm.di.module.DefaultDispatcher
import com.smvs.gkm.di.module.IODispatcher
import com.smvs.gkm.di.module.MainDispatcher
import com.smvs.gkm.di.module.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatchersModule {

    @IODispatcher
    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @UnconfinedDispatcher
    @Provides
    fun providesUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined
}