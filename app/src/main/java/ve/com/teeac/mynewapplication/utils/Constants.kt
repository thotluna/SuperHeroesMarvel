package ve.com.teeac.mynewapplication.utils

import ve.com.teeac.mynewapplication.BuildConfig
import java.math.BigInteger
import java.security.MessageDigest
import java.sql.Timestamp

class Constants {

    companion object{
        const val BASE_URL = "https://gateway.marvel.com:443/v1/public/"
        const val API_KEY = BuildConfig.MARVEL_PUBLIC_KEY
        val ts = Timestamp(System.currentTimeMillis()).time.toString()
        const val limit = "20"
        fun hash(): String {
            val input = "$ts${BuildConfig.MARVEL_PRIVATE_KEY}$API_KEY"
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}