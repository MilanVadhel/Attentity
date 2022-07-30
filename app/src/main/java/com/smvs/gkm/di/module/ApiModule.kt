package com.smvs.gkm.di.module

import com.smvs.gkm.data.admin.repository.AdminApi
import com.smvs.gkm.data.auth.repository.AuthApi
import com.smvs.gkm.data.main.attendance.repository.AttendanceApi
import com.smvs.gkm.data.main.report.repository.ReportApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAttendanceApi(retrofit: Retrofit): AttendanceApi {
        return retrofit.create(AttendanceApi::class.java)
    }

    @Singleton
    @Provides
    fun providesReportApi(retrofit: Retrofit): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }

    @Singleton
    @Provides
    fun providesAdminApi(retrofit: Retrofit): AdminApi {
        return retrofit.create(AdminApi::class.java)
    }

}