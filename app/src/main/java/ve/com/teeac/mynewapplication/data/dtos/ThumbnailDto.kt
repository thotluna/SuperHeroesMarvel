package ve.com.teeac.mynewapplication.data.dtos

import ve.com.teeac.mynewapplication.domain.models.Thumbnail

data class ThumbnailDto(
    val extension: String = "",
    val path: String = "image_"
) {
    fun toThumbnail(): Thumbnail {
        return Thumbnail(
            extension = extension,
            path = path
        )
    }
}
