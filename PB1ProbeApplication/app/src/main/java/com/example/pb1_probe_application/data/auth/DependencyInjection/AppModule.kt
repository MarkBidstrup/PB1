package com.example.pb1_probe_application.data.auth.DependencyInjection

import com.example.pb1_probe_application.data.auth.AuthRepository
import com.example.pb1_probe_application.data.auth.AuthRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// code from: https://www.youtube.com/watch?v=LHh2_TXBmS8&t=1662s&ab_channel=SimplifiedCoding

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideAuthRepository(impl: AuthRepositoryImpl): AuthRepository = impl
}