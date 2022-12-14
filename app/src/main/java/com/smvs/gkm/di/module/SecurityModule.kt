package com.smvs.gkm.di.module

import android.content.Context
import android.content.SharedPreferences
import com.app.gofood.data.sharedprefs.SecurityGuards
import com.smvs.gkm.data.sharedprefs.SharedPrefs
import com.smvs.gkm.data.sharedprefs.securityguards.SecurityGuardsImpl
import com.smvs.gkm.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SecurityModule {

    @Singleton
    @Provides
    fun provideSecurityGuards(@ApplicationContext context: Context): SecurityGuards {
        return SecurityGuardsImpl.Factory().create(context)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        val versionSecurityGuards: Int = BuildConfig.SECURITY_GUARD_VERSION
        val cacheName = StringBuilder().append(
            SharedPrefs.PREFS_NAME
        ).append(
            versionSecurityGuards.toString()
        ).toString()
        return context.getSharedPreferences(cacheName, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPrefs(
        sharedPreferences: SharedPreferences,
        securityGuards: SecurityGuards
    ): SharedPrefs {
        return SharedPrefs(sharedPreferences, securityGuards)
    }
}
