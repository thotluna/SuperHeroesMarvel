package ve.com.teeac.mynewapplication.data.dtos

data class CharacterDto(
    val comics: Group<Item>,
    val description: String,
    val events: Group<Item>,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Group<Item>,
    val stories: Group<ItemXXX>,
    val thumbnail: ThumbnailDto,
    val urls: List<Url>
)