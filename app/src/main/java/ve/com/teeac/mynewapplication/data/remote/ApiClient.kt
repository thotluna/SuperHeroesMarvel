package ve.com.teeac.mynewapplication.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

fun apiClient() = OkHttpClient()
    .newBuilder()
    .addInterceptor(AuthInterceptor())
    .addInterceptor(ChangeProtocolInterceptor())
    .addInterceptor(HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    })
    .build()
