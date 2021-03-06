package ve.com.teeac.mynewapplication.domain.use_cases

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Response

class GetCharacterById @Inject constructor(
    private val repository: CharactersRepository
) {
    operator fun invoke(id: Int): Flow<Response<Character>> = flow {
        emit(Response.Loading())
        try {
            val character = repository.getCharacterById(id)
            emit(Response.Success(character))
        } catch (e: Exception) {
            Timber.e("Error in GetCharacterById: ${e.message}")
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }
}
