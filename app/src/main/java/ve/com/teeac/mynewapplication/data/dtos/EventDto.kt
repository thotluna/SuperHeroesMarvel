package ve.com.teeac.mynewapplication.data.dtos


data class EventDto(
    val id: Int,
    val title: String,
    val description: String = "",
    val thumbnail: ThumbnailDto?
)