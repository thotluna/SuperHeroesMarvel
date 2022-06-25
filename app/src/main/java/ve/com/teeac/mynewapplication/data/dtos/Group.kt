package ve.com.teeac.mynewapplication.data.dtos

data class Group<T>(
    val available: Int,
    val collectionURI: String,
    val items: List<T>,
    val returned: Int
)