package ve.com.teeac.mynewapplication.di

import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import ve.com.teeac.mynewapplication.data.local.AppDatabase
import ve.com.teeac.mynewapplication.data.local.ItemsLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.data.remote.ItemsRemoteDataSource
import ve.com.teeac.mynewapplication.data.repositories.ItemsRepositoryImp
import ve.com.teeac.mynewapplication.domain.models.TypeItem
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ModuleItemsData {

    @Provides
    @Singleton
    @ComicRepositoryImp
    fun provideComicRepositoryImpl( db: AppDatabase, api: ApiService ): ItemsRepositoryImp {
        val type = TypeItem.COMICS.name
        val localDataSource = ItemsLocalDataSource(db.itemsDao, type)
        val remoteDataSource = ItemsRemoteDataSource( api, type )

        return ItemsRepositoryImp(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    @EventRepositoryImp
    fun provideEventRepositoryImpl( db: AppDatabase, api: ApiService ): ItemsRepositoryImp {
        val type = TypeItem.EVENTS.name
        val localDataSource = ItemsLocalDataSource(db.itemsDao, type)
        val remoteDataSource = ItemsRemoteDataSource( api, type )

        return ItemsRepositoryImp(localDataSource, remoteDataSource)
    }

    @Provides
    @Singleton
    @SeriesRepositoryImp
    fun provideSeriesRepositoryImpl( db: AppDatabase, api: ApiService ): ItemsRepositoryImp {
        val type = TypeItem.SERIES.name
        val localDataSource = ItemsLocalDataSource(db.itemsDao, type)
        val remoteDataSource = ItemsRemoteDataSource( api, type )

        return ItemsRepositoryImp(localDataSource, remoteDataSource)
    }

}