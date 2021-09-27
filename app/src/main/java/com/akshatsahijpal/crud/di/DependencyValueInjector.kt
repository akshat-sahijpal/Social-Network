package com.akshatsahijpal.crud.di

import android.content.Context
import com.akshatsahijpal.crud.repository.login.UserRegisterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DependencyValueInjector {
    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext cont: Context) = UserRegisterRepository(cont)

}