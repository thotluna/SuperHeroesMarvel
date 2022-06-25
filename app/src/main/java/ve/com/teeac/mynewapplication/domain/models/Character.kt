package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.data.dtos.Thumbnail


data class Character(
    val id: Int,
    val name: String,
    val thumbnail: Thumbnail,
    val description: String,
    val comics: List<Item>,
    val events: List<Item>,
    val series: List<Item>,
    val stories: List<Item>,
)
