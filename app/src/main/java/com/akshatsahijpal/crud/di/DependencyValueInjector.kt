package com.akshatsahijpal.crud.di

import android.content.Context
import com.akshatsahijpal.crud.repository.login.UserRegisterRepository
import com.akshatsahijpal.crud.repository.main.HomeScreenRepository
import com.akshatsahijpal.crud.repository.post.PostCreationRepository
import com.akshatsahijpal.crud.ui.fragments.profile.tabs.qa.repository.QuestionsAnsweredTabRepository
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

    @Singleton
    @Provides
    fun providePostCreationRepository() = PostCreationRepository()

    @Singleton
    @Provides
    fun provideHomeScreenRepository() = HomeScreenRepository()

    @Singleton
    @Provides
    fun provideQuestionsAnsweredTabRepository() = QuestionsAnsweredTabRepository()
}