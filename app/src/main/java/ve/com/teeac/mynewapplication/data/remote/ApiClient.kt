package ve.com.teeac.mynewapplication.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

val interceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun apiClient() = OkHttpClient()
    .newBuilder()
    .addInterceptor(HttpLoggingInterceptor())
    .build()

