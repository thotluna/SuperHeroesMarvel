package ve.com.teeac.mynewapplication.domain.repositories

import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem

interface CharactersRepository {

    suspend fun getCharacters(
        nameStartsWith: String? = null,
        forceUpdate: Boolean = false
    ): List<CharacterItem>

    suspend fun getCharacterById(id: Int): Character
}
