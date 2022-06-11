package ve.com.teeac.mynewapplication.data.dtos

data class Events(
    val available: Int,
    val collectionURI: String,
    val items: List<Item>,
    val returned: Int
)