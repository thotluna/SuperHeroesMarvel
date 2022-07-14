package ve.com.teeac.mynewapplication.presentations.characteres

import ve.com.teeac.mynewapplication.domain.models.CharacterItem

data class CharactersState(
    val nameStartsWith: String? = null,
    val isLoading: Boolean = false,
    val characters: List<CharacterItem> = listOf(),
    val error: String = "",
    val isRefresh: Boolean = false
)
