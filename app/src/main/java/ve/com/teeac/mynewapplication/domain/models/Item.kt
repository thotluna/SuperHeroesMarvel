package ve.com.teeac.mynewapplication.domain.models

import ve.com.teeac.mynewapplication.data.dtos.Thumbnail

data class Item(
    val id: Int,
    val description: String?,
    val title: String,
    val thumbnail: Thumbnail?,
)
