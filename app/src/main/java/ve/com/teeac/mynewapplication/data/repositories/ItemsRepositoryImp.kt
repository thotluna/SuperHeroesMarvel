package ve.com.teeac.mynewapplication.data.repositories

import ve.com.teeac.mynewapplication.data.local.ItemsLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.ItemsRemoteDataSource
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository
import javax.inject.Inject

class ItemsRepositoryImp @Inject constructor(
    private val local: ItemsLocalDataSource,
    private val remote: ItemsRemoteDataSource
) : ItemsRepository {

    override suspend fun getItemsByCharacterId(id: Int, forceUpdate: Boolean): List<Item> {
        var itemsDto = local.getItemsByCharacterId(id)
        if(itemsDto.isEmpty() || forceUpdate) {
            val itemsRemote = remote.getItemsByCharacterId(id, forceUpdate)
            if(itemsRemote.isEmpty()) {
                return emptyList()
            }else{
                if(forceUpdate) {
                    local.refreshItems(itemsRemote)
                }else{
                    local.insert(itemsRemote)
                }
                itemsDto = local.getItemsByCharacterId(id)
            }
        }
        return itemsDto.map { it.toItem() }
    }

}