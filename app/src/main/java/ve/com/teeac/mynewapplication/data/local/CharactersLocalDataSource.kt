package ve.com.teeac.mynewapplication.data.local

import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.local.daos.CharacterDao
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(
    private val dao: CharacterDao,
) {

    private var page: Int = 0

    suspend fun getCharacter(id: Int): CharacterDto? {
        return dao.getCharacterById(id)
    }

    suspend fun getCharacters(nameStart: String? = null): List<CharacterDto> {
        val offset = page * Constants.CHARACTERS_LIMIT_LOCAL.toInt()
        val list = if (nameStart.isNullOrEmpty()) {
            dao.getAllCharacter(offset = offset)
        } else {
            dao.getAllCharacter(offset = offset, nameStartsWith = nameStart)
        }

        if (list.isNotEmpty() && nameStart.isNullOrEmpty()) page++

        return list
    }

    suspend fun refreshCharacters(characters: List<CharacterDto>) {
        page = 0
        dao.deleteAll()
        insertCharacters(characters)
    }

    suspend fun insertCharacters(characters: List<CharacterDto>) {
        dao.insertAll(characters)
    }

    fun reverseLastPage() {
        page -= 1
    }
}