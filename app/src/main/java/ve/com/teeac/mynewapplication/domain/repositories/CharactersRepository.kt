package ve.com.teeac.mynewapplication.domain.repositories

import ve.com.teeac.mynewapplication.data.dtos.CharacterDataWrapper

interface CharactersRepository{

    suspend fun getCharacters(offset: Int): CharacterDataWrapper
}