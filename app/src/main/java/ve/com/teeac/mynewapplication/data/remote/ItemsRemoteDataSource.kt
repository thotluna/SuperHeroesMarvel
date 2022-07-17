package ve.com.teeac.mynewapplication.data.remote

import androidx.compose.ui.geometry.Offset
import timber.log.Timber
import ve.com.teeac.mynewapplication.data.dtos.DataContainer
import ve.com.teeac.mynewapplication.data.dtos.DataWrapper
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class ItemsRemoteDataSource @Inject constructor(
    private val api: ApiService,
    private val typeItem: String
) {

    private var page: Int = 0
    private var totalRecordsApi = 0
    private var recordsObtained = 0

    suspend fun getItemsByCharacterId(id: Int, forceUpdate: Boolean = false): List<ItemDto> {
        return getItems(id, forceUpdate)
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun getItems(id: Int, forceUpdate: Boolean = false): List<ItemDto> {
        if (forceUpdate) resetProperties()
        if (!hasRecordPending()) return emptyList()

        val items = getItemsFromApi(id)

        updateProperties(items.data)
        return items.data.results
            .filter { !it.thumbnail.path.contains("image_") }
            .map {
                it.copy(
                    idCharacter = id, type = typeItem, thumbnail = ThumbnailDto(
                        it.thumbnail.extension,
                        it.thumbnail.path.replace("http", "https")
                    )
                )
            }

    }

    private suspend fun getItemsFromApi(id: Int): DataWrapper<ItemDto> {
        val offset = (page * Constants.ITEMS_LIMIT.toInt()).toString()
        return when (typeItem) {
            "COMICS" -> api.getComicByCharacterId(id, offset)
            "SERIES" -> api.getSeriesByCharacterId(id, offset)
            "EVENTS" -> api.getEventsByCharacterId(id, offset)
            else -> throw IllegalArgumentException("Invalid type item")
        }
    }

    private fun updateProperties(data: DataContainer<ItemDto>) {
        page++
        totalRecordsApi = data.total
        recordsObtained += data.count
    }

    private fun resetProperties() {
        page = 0
        totalRecordsApi = 0
        recordsObtained = 0
    }

    private fun hasRecordPending(): Boolean {
        if (recordsObtained == 0) return true
        return recordsObtained < totalRecordsApi
    }


}
