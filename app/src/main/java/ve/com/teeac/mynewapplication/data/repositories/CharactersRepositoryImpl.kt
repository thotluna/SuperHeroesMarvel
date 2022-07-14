package ve.com.teeac.mynewapplication.data.repositories

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.local.CharactersLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.CharactersRemoteDataSource
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class CharactersRepositoryImpl
@Inject
constructor(
    private val local: CharactersLocalDataSource,
    private val remote: CharactersRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CharactersRepository {

    override suspend fun getCharacters(
        nameStartsWith: String?,
        forceUpdate: Boolean
    ): List<CharacterItem> {
        if (forceUpdate) {
            val listRemote =
                remote.getCharacters(nameStart = nameStartsWith, forceUpdate = forceUpdate)
            local.refreshCharacters(listRemote)
        }
        var list = local.getCharacters(nameStartsWith)
        if (list.isEmpty() || list.size < Constants.CHARACTERS_LIMIT_LOCAL.toInt()) {
            if(list.isNotEmpty()) local.reverseLastPage()
            val listRemote =
                remote.getCharacters(nameStart = nameStartsWith, forceUpdate = forceUpdate)
            local.insertCharacters(listRemote)
            list = local.getCharacters(nameStartsWith)
        }
        return list.map { it.toCharacterItem() }
    }




    override suspend fun getCharacterById(id: Int): Character {
        var character = local.getCharacter(id)
        if (character != null) {
            val characterRemote = remote.getCharacterById(id)
            local.insertCharacters(listOf(characterRemote))
            character = local.getCharacter(id)
        }
        return character!!.toCharacter()
    }


}