package ve.com.teeac.mynewapplication.data.dtos


data class DataContainer<T>(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<T>,
    val total: Int
)
