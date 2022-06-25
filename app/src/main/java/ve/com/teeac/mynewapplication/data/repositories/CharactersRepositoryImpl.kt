package ve.com.teeac.mynewapplication.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import timber.log.Timber
import ve.com.teeac.mynewapplication.data.remote.ApiService
import ve.com.teeac.mynewapplication.domain.mappers.ItemMapper
import ve.com.teeac.mynewapplication.domain.mappers.ModelsMapper
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.models.Item
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(private val service: ApiService) : CharactersRepository {
    override suspend fun getCharacters(offset: Int): List<CharacterItem> {
        return withContext(Dispatchers.IO) {
            service.getCharacters(offset = offset.toString())
                .data.results.map { characterDto ->
                    ItemMapper.characterDtoToCharacterItem(characterDto)
                }
        }
    }

    override suspend fun getCharacterById(id: Int): Character {
        return withContext(Dispatchers.IO) {
            val characterDto = service.getCharacterById(id).data.results.first()
            return@withContext ModelsMapper.characterDtoToCharacter(characterDto)
        }
    }

    override suspend fun getComicByCharacterId(id: Int): List<Item> {
        return withContext(Dispatchers.IO) {
            service.getComicByCharacterId(id)
                .data.results.map { comicDto ->
                    ItemMapper.comicToDto(comicDto)
                }
        }
    }

    override suspend fun getEventsByCharacterId(id: Int): List<Item> {
        return withContext(Dispatchers.IO) {
            service.getEventsByCharacterId(id)
                .data.results.map { eventDto ->
                    ItemMapper.eventToDto(eventDto)
                }
        }
    }

    override suspend fun getSeriesByCharacterId(id: Int): List<Item> {
        Timber.d("Character: series")
        return withContext(Dispatchers.IO) {
            service.getSeriesByCharacterId(id)
                .data.results.map { seriesDto ->
                    ItemMapper.seriesToDto(seriesDto)
                }
        }
    }

    override suspend fun getStoriesByCharacterId(id: Int): List<Item> {
        Timber.d("Character: stories")
        return withContext(Dispatchers.IO) {
            service.getStoriesByCharacterId(id)
                .data.results.map { storyDto ->
                    ItemMapper.storyToDto(storyDto)
                }
        }
    }

    override suspend fun getCharacter(id: Int): Character {
        return withContext(Dispatchers.IO) {
            val characterDeferred = async { service.getCharacterById(id) }
            val comicDeferred = async { service.getComicByCharacterId(id) }
            val eventsDeferred = async { service.getEventsByCharacterId(id) }
            val seriesDeferred = async { service.getSeriesByCharacterId(id) }
            val storiesDeferred = async { service.getStoriesByCharacterId(id) }

            val characterDto = characterDeferred.await().data.results.first()
            val comicDto = comicDeferred.await().data.results
            val eventsDto = eventsDeferred.await().data.results
            val seriesDto = seriesDeferred.await().data.results
            val storiesDto = storiesDeferred.await().data.results

            Timber.d("Character: $characterDto")
            Timber.d("Character: comics $comicDto")
            Timber.d("Character: events $eventsDto")
            Timber.d("Character: series $seriesDto")
            Timber.d("Character: stories $storiesDto")


            return@withContext ModelsMapper.characterDtoToCharacter(characterDto).copy(
                comics = if (comicDto.isNotEmpty()) {
                    comicDto.map { ItemMapper.comicToDto(it) }
                } else {
                    emptyList()
                },
                events = if (eventsDto.isNotEmpty()) {
                    eventsDto.map { ItemMapper.eventToDto(it) }
                } else {
                    emptyList()
                },
                series = if (seriesDto.isNotEmpty()) {
                    seriesDto.map { ItemMapper.seriesToDto(it) }
                } else {
                    emptyList()
                },
                stories = if(storiesDto.isNotEmpty()){
                    storiesDto.map { ItemMapper.storyToDto(it) }
                }else{
                    emptyList()
                }
            )
        }
    }
}