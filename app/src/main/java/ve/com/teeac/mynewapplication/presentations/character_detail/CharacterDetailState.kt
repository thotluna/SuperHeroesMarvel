package ve.com.teeac.mynewapplication.presentations.character_detail

import ve.com.teeac.mynewapplication.domain.models.Character

data class CharacterDetailState(
    val isLoading: Boolean = false,
    val character: Character = Character(),
    val error: String? = null,
    val id: Int = 0,
    val isRefresh: Boolean = false
)
