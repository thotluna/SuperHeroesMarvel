package ve.com.teeac.mynewapplication.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

open class GetItems @Inject constructor(
    private val repository: ItemsRepository
){

    operator fun invoke(id: Int, forceUpdate: Boolean = false): Flow<Response<List<Item>>> = flow{
        emit(Response.Loading())
        try {
            val items = repository.getItemsByCharacterId(id, forceUpdate)
            emit(Response.Success( items ))
        } catch (e: Exception) {
            Timber.e("Error in GetItems: ${e.message}")
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }

}

