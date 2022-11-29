package com.example.pb1_probe_application.DependencyInjection

import com.example.pb1_probe_application.data.auth.AuthRepository
import com.example.pb1_probe_application.data.auth.AuthRepositoryImpl
import com.example.pb1_probe_application.data.trials.TrialRepository
import com.example.pb1_probe_application.data.trials.TrialRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

    @Provides
    fun provideFirestore(): FirebaseFirestore = Firebase.firestore

    @Provides
    fun provideTrialsRepository(impl: TrialRepositoryImpl): TrialRepository = impl
}