package ve.com.teeac.mynewapplication.data.dtos

data class SeriesDto(
    val id: Int,
    val title: String,
    val description: String = "",
    val thumbnail: ThumbnailDto,
    val characters: Group<Item>,
    val comics: Group<Item>,
    val creators: Group<ItemXX>,
    val endYear: Int,
    val events: Group<Item>,
    val modified: String,
    val next: Item,
    val previous: Item,
    val rating: String,
    val resourceURI: String,
    val startYear: Int,
    val stories: Group<ItemXXX>,
    val type: String,
    val urls: List<Url>
)