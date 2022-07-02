package ve.com.teeac.mynewapplication.data.dtos

data class CharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDto,
//    val comics: Group<Item>,
//    val events: Group<Item>,
//    val modified: String,
//    val resourceURI: String,
//    val series: Group<Item>,
//    val stories: Group<ItemXXX>,
//    val urls: List<Url>
)