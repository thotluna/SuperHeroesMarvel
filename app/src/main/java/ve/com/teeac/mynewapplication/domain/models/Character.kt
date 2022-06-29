package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.data.dtos.Thumbnail


data class Character(
    val id: Int = -1,
    val name: String = "",
    val thumbnail: Thumbnail = Thumbnail("", ""),
    val description: String = "",
    val comics: List<Item> = emptyList(),
    val events: List<Item> = emptyList(),
    val series: List<Item> = emptyList(),
    val stories: List<Item> = emptyList(),
)
