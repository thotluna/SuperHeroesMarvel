package ve.com.teeac.mynewapplication.data.local

import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.dtos.DtoLocalRequestHandler
import ve.com.teeac.mynewapplication.data.local.daos.CharacterDao
import ve.com.teeac.mynewapplication.data.local.daos.LocalRequestHandlerDao
import ve.com.teeac.mynewapplication.utils.Constants
import javax.inject.Inject

class CharactersLocalDataSource @Inject constructor(
    private val dao: CharacterDao,
    private val daoHandler: LocalRequestHandlerDao
) {

    private var page: Int = 0

    suspend fun getCharacter(id: Int): CharacterDto? {
        return dao.getCharacterById(id)
    }

    suspend fun getCharacters(nameStart: String? = null): List<CharacterDto> {
//        initialized()
        val offset = page * Constants.CHARACTERS_LIMIT_LOCAL.toInt()
        val list = if (nameStart.isNullOrEmpty()) {
            dao.getAllCharacter(offset = offset)
        } else {
            dao.getAllCharacter(offset = offset, nameStartsWith = nameStart)
        }

        if (list.isNotEmpty() && nameStart.isNullOrEmpty()) {
//            daoHandler.insert(DtoLocalRequestHandler().copy(page = page.plus(1)))
            page++
        }

        return list
    }

    private suspend fun initialized() {
        if (page == 0) {
            val handler = daoHandler.getHandler()
            handler?.let{
                page = it.page
            }?: run {
                daoHandler.insert(DtoLocalRequestHandler())
            }
        }
    }

    suspend fun refreshCharacters(characters: List<CharacterDto>) {
        dao.deleteAll()
        daoHandler.insert(DtoLocalRequestHandler())
        insertCharacters(characters)
    }

    suspend fun insertCharacters(characters: List<CharacterDto>) {
        dao.insertAll(characters)
    }

    fun reverseLastPage() {
        page -= 1
    }
}