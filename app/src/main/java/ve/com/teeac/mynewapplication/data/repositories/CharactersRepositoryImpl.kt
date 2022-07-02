package ve.com.teeac.mynewapplication.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ve.com.teeac.mynewapplication.data.mappers.ItemMapper
import ve.com.teeac.mynewapplication.data.mappers.ModelsMapper
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(private val service: ApiService) : CharactersRepository {
    override suspend fun getCharacters(offset: Int, nameStartsWith: String): List<CharacterItem> {
        return withContext(Dispatchers.IO) {
            if(nameStartsWith.isEmpty()) {
                service.getCharacters(offset = offset.toString())
                    .data.results.filter{!it.thumbnail.path.contains("image_not_available")}.map { dto ->
                        ItemMapper.characterDtoToCharacterItem(dto)
                    }
            } else {
                service.getCharactersByStartName(offset = offset.toString(), nameStartsWith = nameStartsWith)
                    .data.results.filter{!it.thumbnail.path.contains("image_not_available")}.map { characterDto ->
                        ItemMapper.characterDtoToCharacterItem(characterDto)
                    }
            }
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return withContext(Dispatchers.IO) {
            val dto = service.getCharacterById(id).data.results.first()
            return@withContext ModelsMapper.characterDtoToCharacter(dto)
        }
    }

    override suspend fun getComicByCharacterId(id: Int, offset: Int): List<Item> {
        return withContext(Dispatchers.IO) {
            service.getComicByCharacterId(id, offset.toString())
                .data.results.map { dto ->
                    ItemMapper.comicToDto(dto)
                }
        }
    }

    override suspend fun getEventsByCharacterId(id: Int, offset: Int): List<Item> {
        return withContext(Dispatchers.IO) {
            service.getEventsByCharacterId(id, offset.toString())
                .data.results.map { dto ->
                    ItemMapper.eventToDto(dto)
                }
        }
    }

    override suspend fun getSeriesByCharacterId(id: Int, offset: Int): List<Item> {
        return withContext(Dispatchers.IO) {
            service.getSeriesByCharacterId(id, offset.toString())
                .data.results.map { dto ->
                    ItemMapper.seriesToDto(dto)
                }
        }
    }
}