package ve.com.teeac.mynewapplication.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

class GetCharacterByIdUseCase
@Inject
constructor(private val repository: CharactersRepository) {
    operator fun invoke(id: Int): Flow<Response<Character>> = flow{
        emit(Response.Loading())
        try {
            val characterRes = repository.getCharacterById(id)
            val character = characterRes.data.results
            emit(Response.Success( Character.fromDto(character.first()) ))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }
}