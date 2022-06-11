package ve.com.teeac.mynewapplication.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import ve.com.teeac.mynewapplication.BuildConfig
import ve.com.teeac.mynewapplication.utils.Constants
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class AuthInterceptor : Interceptor {
    private val ts = Timestamp(System.currentTimeMillis()).time.toString()

    override fun intercept(chain: Interceptor.Chain): Response {

        val newUrl = chain.request().url
            .newBuilder()
            .addQueryParameter("ts", ts)
            .addQueryParameter("apikey", BuildConfig.MARVEL_PUBLIC_KEY)
            .addQueryParameter("hash", hash())
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }

    private fun hash(): String {
        val input = "${ts}${BuildConfig.MARVEL_PRIVATE_KEY}${BuildConfig.MARVEL_PUBLIC_KEY}"
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
}