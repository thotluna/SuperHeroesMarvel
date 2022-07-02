package ve.com.teeac.mynewapplication.data.dtos

data class StoryDto(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: ThumbnailDto?,
//    val characters: Group<Item>,
//    val comics: Group<Item>,
//    val creators: Group<ItemXX>,
//    val events: Group<Item>,
//    val modified: String,
//    val originalIssue: Item,
//    val resourceURI: String,
//    val series: Group<Item>,
//    val type: String
)