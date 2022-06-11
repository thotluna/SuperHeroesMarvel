package ve.com.teeac.mynewapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ve.com.teeac.mynewapplication.data.dtos.CharacterDataWrapper
import ve.com.teeac.mynewapplication.utils.Constants

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("apikey") apiKey: String = Constants.API_KEY,
        @Query("ts") ts: String = Constants.ts,
        @Query("hash") hash: String = Constants.hash(),
        @Query("offset") offset: String = "0"
    ): CharacterDataWrapper

}