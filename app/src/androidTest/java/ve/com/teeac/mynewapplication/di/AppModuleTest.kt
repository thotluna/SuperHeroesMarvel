package ve.com.teeac.mynewapplication.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ve.com.teeac.mynewapplication.data.local.AppDatabase
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.data.remote.apiClient
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ModuleApp::class]
)
object AppModuleTest {

    @Singleton
    @Provides
    @ApiAddress
    fun provideApiAddress(): String = "http://localhost:8080/api/"

    @Singleton
    @Provides
    fun providerApiService(@ApiAddress url: String): ApiService {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(apiClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            AppDatabase::class.java,
        ).build()
    }

}