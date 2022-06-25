package ve.com.teeac.mynewapplication.data.dtos

data class EventDto(
    val characters: Group<Item>,
    val comics: Group<Item>,
    val creators: Group<ItemXX>,
    val description: String = "",
    val end: String,
    val id: Int,
    val modified: String,
    val next: Item,
    val previous: Item,
    val resourceURI: String,
    val series: Group<Item>,
    val start: String,
    val stories: Group<ItemXXXX>,
    val thumbnail: Thumbnail?,
    val title: String,
    val urls: List<Url>
)