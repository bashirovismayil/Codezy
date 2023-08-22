package com.bashir.codezy.data.di.module

import android.content.Context
import androidx.room.Room
import com.bashir.codezy.data.codezydb.AppDatabase
import com.bashir.codezy.data.dao.UserDao
import com.bashir.codezy.data.source.FirebaseAuthDataSource
import com.bashir.codezy.domain.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {

    @Provides
    @Singleton
    fun userDao(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "AppDatabase")
            .fallbackToDestructiveMigration().build().userDao()

    @Provides
    @Singleton
    fun firebaseAuth() = Firebase.auth

    @Provides
    @Singleton
    fun firebaseFirestore() = Firebase.firestore


    @Provides
    @Singleton
    fun dataSource(auth: FirebaseAuth) = FirebaseAuthDataSource(auth)

    @Provides
    @Singleton
    fun userRepository(dataSource: FirebaseAuthDataSource, dao: UserDao) =
        UserRepository(dataSource, dao)


}