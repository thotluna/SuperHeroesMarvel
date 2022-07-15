package ve.com.teeac.mynewapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ve.com.teeac.mynewapplication.data.local.AppDatabase
import ve.com.teeac.mynewapplication.data.local.CharactersLocalDataSource
import ve.com.teeac.mynewapplication.data.local.daos.RemoteRequestHandlerDao
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.data.remote.CharactersRemoteDataSource
import ve.com.teeac.mynewapplication.data.repositories.CharactersRepositoryImpl
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ModuleCharacterData {


    @Provides
    @Singleton
    fun provideRequestHandlerDao(db: AppDatabase): RemoteRequestHandlerDao {
        return db.requestRemoteHandlerDao
    }

    @Provides
    @Singleton
    fun providerCharacterLocalDataSource(db: AppDatabase): CharactersLocalDataSource {
        return CharactersLocalDataSource(
            dao = db.characterDao,
        )
    }

    @Provides
    @Singleton
    fun providerCharacterRemoteDataSource(
        api: ApiService,
        db: AppDatabase
    ): CharactersRemoteDataSource {
        return CharactersRemoteDataSource(api = api, local = db.requestRemoteHandlerDao)
    }

    @Singleton
    @Provides
    fun providerChartersRepository(
        local: CharactersLocalDataSource,
        remote: CharactersRemoteDataSource
    ): CharactersRepository {
        return CharactersRepositoryImpl(local, remote)
    }




}