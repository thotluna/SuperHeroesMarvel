package ve.com.teeac.mynewapplication.domain.models

data class Item(
    val id: Int,
    val description: String?,
    val title: String,
    val thumbnail: Thumbnail?,
)
