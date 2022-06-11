package ve.com.teeac.mynewapplication.domain.use_cases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Response
import javax.inject.Inject

class GetCharactersUseCase
@Inject
constructor(private val repository: CharactersRepository) {
    operator fun invoke(offset: Int): Flow<Response<List<Character>>> = flow{
        emit(Response.Loading())
        try {
            val characters = repository.getCharacters(offset)
            val listDto = characters.data.results
            emit(Response.Success(listDto.map { Character.fromDto(it) }))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "Error: ${e.printStackTrace()}"))
        }
    }
}
