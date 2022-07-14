package ve.com.teeac.mynewapplication.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

class GetCharacters @Inject constructor(
    private val repository: CharactersRepository
) {
    operator fun invoke(nameStartsWith: String? = null, forceUpdate: Boolean = false) : Flow<Response<List<CharacterItem>>> = flow{
        emit(Response.Loading())
        try {
            val characters = repository.getCharacters(nameStartsWith = nameStartsWith, forceUpdate = forceUpdate)
            emit(Response.Success(characters))
        } catch (e: Exception) {
            Timber.e("Error in GetCharacters: ${e.message}")
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }
}