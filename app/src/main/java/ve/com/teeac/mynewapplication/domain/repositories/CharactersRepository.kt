package ve.com.teeac.mynewapplication.domain.repositories

import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.models.Item

interface CharactersRepository{

    suspend fun getCharacters(offset: Int, nameStartsWith: String = ""): List<CharacterItem>

    suspend fun getCharacterById(id: Int): Character

    suspend fun getComicByCharacterId(id: Int, offset: Int = 0): List<Item>

    suspend fun getEventsByCharacterId(id: Int, offset: Int = 0): List<Item>

    suspend fun getSeriesByCharacterId(id: Int, offset: Int = 0): List<Item>

}