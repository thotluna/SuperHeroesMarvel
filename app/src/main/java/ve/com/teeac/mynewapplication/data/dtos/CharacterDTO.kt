package ve.com.teeac.mynewapplication.data.dtos

data class CharacterDto(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: ThumbnailDto,
)