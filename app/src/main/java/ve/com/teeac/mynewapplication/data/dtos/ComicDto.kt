package ve.com.teeac.mynewapplication.data.dtos

data class ComicDto(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: ThumbnailDto
)