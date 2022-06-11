package ve.com.teeac.mynewapplication.data.dtos

data class CharacterDataContainer(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<CharacterDTO>,
    val total: Int
)