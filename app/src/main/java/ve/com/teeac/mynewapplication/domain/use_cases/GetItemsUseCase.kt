package ve.com.teeac.mynewapplication.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

class GetItemsUseCase @Inject
constructor(
    private val repository: CharactersRepository
){

    operator fun invoke(id: Int, offset: Int = 0, type: TypeItem): Flow<Response<List<Item>>> = flow{
        emit(Response.Loading())
        try {
            val items = getItems(id, offset, type)
            emit(Response.Success( items ))
        } catch (e: Exception) {
            Timber.e(e)
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }

    private suspend fun getItems(id: Int, offset: Int, type: TypeItem ): List<Item>{
        return when(type){
            TypeItem.COMICS -> repository.getComicByCharacterId(id, offset)
            TypeItem.SERIES -> repository.getSeriesByCharacterId(id, offset)
            TypeItem.EVENTS -> repository.getEventsByCharacterId(id, offset)
        }
    }
}

sealed class TypeItem{
    object COMICS: TypeItem()
    object EVENTS: TypeItem()
    object SERIES: TypeItem()
}