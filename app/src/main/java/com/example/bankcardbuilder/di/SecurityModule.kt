package com.example.bankcardbuilder.di

import com.example.bankcardbuilder.features.domain.SecurityUtils
import com.example.bankcardbuilder.features.data.security.SecurityUtilsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SecurityModule {

    @Provides
    @Singleton
     fun provideSecurityUtils() : SecurityUtils {
         return SecurityUtilsImpl()
     }
}