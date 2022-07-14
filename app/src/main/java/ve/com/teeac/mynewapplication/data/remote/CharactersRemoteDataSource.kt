package ve.com.teeac.mynewapplication.data.remote

import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.dtos.DtoRemoteRequestHandler
import ve.com.teeac.mynewapplication.data.local.daos.RemoteRequestHandlerDao
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class CharactersRemoteDataSource @Inject constructor(
    private val api: ApiService,
    private val local: RemoteRequestHandlerDao
) {

    private var page = 0
    private var totalRecordsApi = 0
    private var recordsObtained = 0

    private val offset = (page * Constants.CHARACTERS_LIMIT_REMOTE.toInt()).toString()

    suspend fun getCharacterById(id:Int): CharacterDto{
        return api.getCharacterById(id).data.results.first { !it.thumbnail.path.contains("image_") }
    }

    suspend fun getCharacters(
        forceUpdate: Boolean = false,
        nameStart: String? = null
    ): List<CharacterDto> {
        if (forceUpdate && nameStart.isNullOrBlank()) {
            restart()
        } else if (checkFull()) {
            return emptyList()
        }
        return if (nameStart.isNullOrBlank()) {
            getCharactersByApi()
        } else {
            getCharactersByNameStart(nameStart)
        }
    }

    private suspend fun restart() {
        page = 0
        totalRecordsApi = 0
        recordsObtained = 0
        updateData()
    }

    private suspend fun updateData() {
        val dto =
            DtoRemoteRequestHandler(
                id = 1,
                page = page,
                recordsObtained = recordsObtained,
                totalRecordsApi = totalRecordsApi
            )
        local.insert(dto)
    }

    private suspend fun populateProperty() {
        if (totalRecordsApi == 0) {
           local.getHandler()?.let{
               page = it.page
               totalRecordsApi = it.totalRecordsApi
               recordsObtained = it.recordsObtained
           }

        }
    }

    private suspend fun checkFull(): Boolean {
        populateProperty()
        return totalRecordsApi in 1..recordsObtained
    }

    private suspend fun getCharactersByNameStart(nameStart: String): List<CharacterDto> {
        return api.getCharactersByStartName(nameStartsWith = nameStart)
            .data.results.filter { !it.thumbnail.path.contains("image_") }
    }

    private suspend fun getCharactersByApi(): List<CharacterDto> {
        val offset = page * Constants.CHARACTERS_LIMIT_REMOTE.toInt()
        val characterContent = api.getCharacters(offset = offset.toString())
        val characters =
            characterContent.data.results.filter { !it.thumbnail.path.contains("image_") }
        page++
        totalRecordsApi = characterContent.data.total
        recordsObtained += characterContent.data.count
        updateData()
        return characters
    }


}