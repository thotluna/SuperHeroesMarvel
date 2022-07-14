package ve.com.teeac.mynewapplication.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ve.com.teeac.mynewapplication.data.repositories.ItemsRepositoryImp
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class ModuleBinds {

    @ComicRepository
    @Binds
    abstract fun bindComicRepository(
        @ComicRepositoryImp itemsRepositoryImp: ItemsRepositoryImp
    ): ItemsRepository

    @EventRepository
    @Binds
    abstract fun bindEventRepository(
        @EventRepositoryImp itemsRepositoryImp: ItemsRepositoryImp
    ): ItemsRepository

    @SeriesRepository
    @Binds
    abstract fun bindSeriesRepository(
        @SeriesRepositoryImp itemsRepositoryImp: ItemsRepositoryImp
    ): ItemsRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ComicRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EventRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SeriesRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ComicRepositoryImp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EventRepositoryImp

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SeriesRepositoryImp

