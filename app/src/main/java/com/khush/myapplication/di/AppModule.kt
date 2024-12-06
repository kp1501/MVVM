package com.khush.myapplication.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.khush.myapplication.data.local.AppDatabase
import com.khush.myapplication.data.remote.AppApi
import com.khush.myapplication.data.remote.BaseDataSource
import com.khush.myapplication.data.repository.AppRepoImpl
import com.khush.myapplication.domain.AppRepository
import com.khush.myapplication.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    // Provides the application repository instance
    @Provides
    fun provideAppRepository(appDatabase: AppDatabase, appApi: AppApi): AppRepository {
        return AppRepoImpl(appDatabase, appApi)
    }

    // Provides the instance of the database
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase =
        Room.databaseBuilder(app, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()

    // Provides the context of the application
    @Provides
    fun provideAppContext(app: Application): Context {
        return app
    }

    // Generates Retrofit and Provides the instance of the Retrofit
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Provides the instance of the baseDataSource
    @Singleton
    @Provides
    fun provideBaseDataSource() : BaseDataSource {
        return BaseDataSource()
    }

    // Generates OkHttpClient and Provides the instance of the OkHttpClient
    @Singleton
    @Provides
    fun okHttpClient(): OkHttpClient {
        val levelType: HttpLoggingInterceptor.Level =
            HttpLoggingInterceptor.Level.BODY

        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)

        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()

        httpClient.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Content-Type", "application/json")
                .build()

            chain.proceed(requestBuilder)
        })


        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addInterceptor(logging)
        return httpClient.build()
    }

    @Singleton
    @Provides
    fun providesAppApi(retrofit: Retrofit): AppApi {
        return retrofit.create(AppApi::class.java)
    }
}