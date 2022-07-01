package ve.com.teeac.mynewapplication.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.utils.Constants

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(
        @Query("offset") offset: String = "0",
        @Query("limit") limit: String = Constants.limit
    ): DataWrapper<CharacterDto>

    @GET("characters")
    suspend fun getCharactersByStartName(
        @Query("nameStartsWith") nameStartsWith: String,
        @Query("offset") offset: String = "0",
        @Query("limit") limit: String = Constants.limit
    ): DataWrapper<CharacterDto>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): DataWrapper<CharacterDto>

    @GET("characters/{id}/comics")
    suspend fun getComicByCharacterId(
        @Path("id") id: Int
    ): DataWrapper<ComicDto>

    @GET("characters/{id}/events")
    suspend fun getEventsByCharacterId(
        @Path("id") id: Int
    ): DataWrapper<EventDto>

    @GET("characters/{id}/series")
    suspend fun getSeriesByCharacterId(
        @Path("id") id: Int
    ): DataWrapper<SeriesDto>

    @GET("characters/{id}/stories")
    suspend fun getStoriesByCharacterId(
        @Path("id") id: Int
    ): DataWrapper<StoryDto>

}