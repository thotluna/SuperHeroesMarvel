package ve.com.teeac.mynewapplication.data.remote

import timber.log.Timber
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class ItemsRemoteDataSource @Inject constructor(
    private val api: ApiService,
    private val typeItem: String
) {

    private var page: Int = 0

    suspend fun getItemsByCharacterId(id: Int, forceUpdate: Boolean = false): List<ItemDto> {
        val items = getItems(id, forceUpdate)
        if (items.isNotEmpty()) page++
        return items
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun getItems(id: Int, forceUpdate: Boolean = false): List<ItemDto> {
        if (forceUpdate) page = 0
        val offset = (page * Constants.ITEMS_LIMIT.toInt()).toString()
        val items = when (typeItem) {
            "COMICS" -> api.getComicByCharacterId(id, offset)
            "SERIES" -> api.getSeriesByCharacterId(id, offset)
            "EVENTS" -> api.getEventsByCharacterId(id, offset)
            else -> throw IllegalArgumentException("Invalid type item")
        }
        Timber.d("Items: $items")

        return if (items.data.results.isEmpty()) {
            emptyList()
        } else {
            items.data.results
                .filter { !it.thumbnail.path.contains("image_") }
                .map { it.copy(idCharacter = id, type = typeItem) }
        }
    }


}
