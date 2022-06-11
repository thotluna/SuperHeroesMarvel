package ve.com.teeac.mynewapplication.data.dtos

data class CharacterDataWrapper(
    val attributionHTML: String,
    val attributionText: String,
    val code: Int,
    val copyright: String,
    val `data`: CharacterDataContainer,
    val etag: String,
    val status: String
)