package ve.com.teeac.mynewapplication.data.dtos

import ve.com.teeac.mynewapplication.data.dtos.Item
import ve.com.teeac.mynewapplication.data.dtos.ItemXX
import ve.com.teeac.mynewapplication.data.dtos.Group

data class StoryDto(
    val characters: Group<Item>,
    val comics: Group<Item>,
    val creators: Group<ItemXX>,
    val description: String,
    val events: Group<Item>,
    val id: Int,
    val modified: String,
    val originalIssue: Item,
    val resourceURI: String,
    val series: Group<Item>,
    val thumbnail: Thumbnail,
    val title: String,
    val type: String
)