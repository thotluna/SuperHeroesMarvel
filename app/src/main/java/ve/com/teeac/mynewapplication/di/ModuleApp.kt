package ve.com.teeac.mynewapplication.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ve.com.teeac.mynewapplication.data.local.AppDatabase
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.data.remote.apiClient
import ve.com.teeac.mynewapplication.utils.Constants

@Module
@InstallIn(SingletonComponent::class)
object ModuleApp {

    @Singleton
    @Provides
    fun providerApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(apiClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }
}
