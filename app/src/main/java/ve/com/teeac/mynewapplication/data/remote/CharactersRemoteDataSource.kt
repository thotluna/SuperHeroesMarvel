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

    suspend fun getCharacterById(id:Int): CharacterDto{
        return api.getCharacterById(id).data.results.first()
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
        saveHandler()
    }

    private suspend fun checkFull(): Boolean {
        populateProperty()
        return totalRecordsApi in 1..recordsObtained
    }

    private suspend fun getCharactersByApi(): List<CharacterDto> {
        val characterContent = api.getCharacters(offset = getOffset()).data

        updateProperty( characterContent.total, characterContent.count)
        return characterContent.results.filter { !it.thumbnail.path.contains("image_") }
    }

    private suspend fun getCharactersByNameStart(nameStart: String): List<CharacterDto> {
        return api.getCharactersByStartName(nameStartsWith = nameStart)
            .data.results.filter { !it.thumbnail.path.contains("image_") }
    }

    private suspend fun updateProperty( totalRecordsApi: Int, recordsObtained: Int) {
        page++
        this.totalRecordsApi = totalRecordsApi
        this.recordsObtained += recordsObtained

        saveHandler()
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

    private suspend fun saveHandler() {
        local.insert(DtoRemoteRequestHandler(
            id = 1,
            page = page,
            recordsObtained = recordsObtained,
            totalRecordsApi = totalRecordsApi
        ))
    }

    private fun getOffset() = (page * Constants.CHARACTERS_LIMIT_REMOTE.toInt()).toString()

}