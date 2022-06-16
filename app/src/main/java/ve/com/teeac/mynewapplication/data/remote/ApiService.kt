package ve.com.teeac.mynewapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ve.com.teeac.mynewapplication.data.dtos.CharacterDataWrapper
import ve.com.teeac.mynewapplication.utils.Constants

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: String = "0",
        @Query("limit") limit: String = Constants.limit
    ): CharacterDataWrapper

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDataWrapper

}