package ve.com.teeac.mynewapplication.presentations.characteres

import ve.com.teeac.mynewapplication.domain.models.CharacterItem

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<CharacterItem> = listOf(),
    val error: String = "",
    val offset: Int = 0,
    val isRefresh: Boolean = false
)
