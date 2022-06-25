package ve.com.teeac.mynewapplication.data.dtos

open class DataWrapper<T>(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: DataContainer<T>,
    val etag: String,
    val status: String
)
