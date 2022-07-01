package ve.com.teeac.mynewapplication.data.dtos

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
    val thumbnail: ThumbnailDto,
    val title: String,
    val type: String
)