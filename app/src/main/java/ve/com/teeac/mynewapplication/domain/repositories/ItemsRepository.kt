package ve.com.teeac.mynewapplication.domain.repositories

import ve.com.teeac.mynewapplication.domain.models.Item

interface ItemsRepository {
    suspend fun getItemsByCharacterId(id: Int, forceUpdate: Boolean = false) : List<Item>
}