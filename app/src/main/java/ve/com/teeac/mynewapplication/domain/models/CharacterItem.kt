package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.Thumbnail

data class CharacterItem(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
)
