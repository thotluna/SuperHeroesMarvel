package ve.com.teeac.mynewapplication.data.local

import javax.inject.Inject
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.local.daos.ItemDao
import ve.com.teeac.mynewapplication.utils.Constants

class ItemsLocalDataSource @Inject constructor(
    private val dao: ItemDao,
    private val typeItem: String
) {

    private var page: Int = 0

    suspend fun getItemsByCharacterId(id: Int): List<ItemDto> {
        val offset = page * Constants.ITEMS_LIMIT.toInt()
        val items = dao.getByCharacterId(id, typeItem, offset)
        if (items.isNotEmpty()) {
            page++
        }
        return items
    }

    suspend fun refreshItems(items: List<ItemDto>) {
        deleteAll()
        insert(items)
    }

    suspend fun insert(items: List<ItemDto>) {
        dao.insert(items)
    }

    private suspend fun deleteAll() {
        page = 0
        dao.deleteAll()
    }
}
