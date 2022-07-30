package com.smvs.gkm.di.module

import com.smvs.gkm.data.admin.repository.AdminRepositoryImpl
import com.smvs.gkm.data.auth.repository.AuthRepositoryImpl
import com.smvs.gkm.data.main.attendance.repository.AttendanceRepositoryImpl
import com.smvs.gkm.data.main.report.repository.ReportRepositoryImpl
import com.smvs.gkm.domain.admin.repository.AdminRepository
import com.smvs.gkm.domain.attendance.repository.AttendanceRepository
import com.smvs.gkm.domain.auth.repository.AuthRepository
import com.smvs.gkm.domain.report.repository.ReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesAttendeesRepository(
        attendanceRepositoryImpl: AttendanceRepositoryImpl
    ): AttendanceRepository

    @Singleton
    @Binds
    abstract fun providesReportRepository(
        reportRepositoryImpl: ReportRepositoryImpl
    ): ReportRepository

    @Singleton
    @Binds
    abstract fun providesAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun providesAdminRepository(
        adminRepositoryImpl: AdminRepositoryImpl
    ): AdminRepository

}