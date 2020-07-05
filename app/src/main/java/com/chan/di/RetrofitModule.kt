package com.chan.di

import com.chan.BuildConfig
import com.chan.network.MockInterceptor
import com.chan.network.api.GoodChoiceApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class RetrofitModule {

    @Provides
    @Singleton
    fun provideHttpLogging() = HttpLoggingInterceptor().apply {
        level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
    }

    @Provides
    @Singleton
    fun provideKotlinJsonAdapterFactory() = KotlinJsonAdapterFactory()

    @Provides
    @Singleton
    fun provideMoshi(jsonAdapter: KotlinJsonAdapterFactory): Moshi =
        Moshi.Builder().add(jsonAdapter).build()

    @Provides
    @Singleton
    fun provideMoshiConverter(moshi: Moshi) = MoshiConverterFactory.create(moshi)

    @Provides
    @Singleton
    fun provideMockInterceptor() = MockInterceptor()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        mockInterceptor: MockInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(mockInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient,
        converterFactory: MoshiConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(converterFactory)
        .build()

    @Provides
    @Singleton
    fun provideGoodChoiceService(retrofit: Retrofit): GoodChoiceApi =
        retrofit.create(GoodChoiceApi::class.java)

    companion object {
        private const val BASE_URL = "https://www.gccompany.co.kr/"
    }
}