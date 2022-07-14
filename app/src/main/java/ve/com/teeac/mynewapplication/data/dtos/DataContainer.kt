package ve.com.teeac.mynewapplication.data.dtos


data class DataContainer<T>(
    val total: Int,
    val count: Int,
    val results: List<T>,
)
