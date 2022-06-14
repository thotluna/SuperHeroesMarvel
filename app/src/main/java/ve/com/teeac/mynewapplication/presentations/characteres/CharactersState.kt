package ve.com.teeac.mynewapplication.presentations.characteres

import ve.com.teeac.mynewapplication.domain.models.Character

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<Character> = listOf(),
    val error: String = "",
    val offset: Int = 0,
    val isRefresh: Boolean = false
)
