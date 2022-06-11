package ve.com.teeac.mynewapplication.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.data.dtos.CharacterDataWrapper
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(private val service: ApiService): CharactersRepository
{
    override suspend fun getCharacters(offset: Int): CharacterDataWrapper {
        return withContext(Dispatchers.IO) {
            service.getCharacters(offset = offset.toString())
        }
    }

}