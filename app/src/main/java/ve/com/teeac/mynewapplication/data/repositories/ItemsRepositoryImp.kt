package ve.com.teeac.mynewapplication.data.repositories

import javax.inject.Inject
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.local.ItemsLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.ItemsRemoteDataSource
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository

class ItemsRepositoryImp @Inject constructor(
    private val local: ItemsLocalDataSource,
    private val remote: ItemsRemoteDataSource
) : ItemsRepository {

    override suspend fun getItemsByCharacterId(id: Int, forceUpdate: Boolean): List<Item> {
        var itemsDto = local.getItemsByCharacterId(id)
        if (itemsDto.isEmpty() || forceUpdate) {
            itemsDto = getByIdCharacterFromRemote(id, forceUpdate)
        }
        return itemsDto.map { it.toItem() }
    }

    private suspend fun getByIdCharacterFromRemote(id: Int, forceUpdate: Boolean): List<ItemDto> {
        val list = remote.getItemsByCharacterId(id, forceUpdate)
        if (list.isEmpty()) return emptyList()
        if (forceUpdate) {
            local.refreshItems(list)
        } else {
            local.insert(list)
        }
        return list
    }
}
