
package ve.com.teeac.mynewapplication.data.dtos

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ve.com.teeac.mynewapplication.domain.models.Item

@Entity(tableName = "items")
data class ItemDto(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "id_character")
    val idCharacter: Int? = null,
    val type: String? = "",
    val title: String? = "",
    val description: String? = "",
    @Embedded(prefix = "image_")
    val thumbnail: ThumbnailDto = ThumbnailDto()
) {

    fun toItem(): Item {
        return Item(
            id = id,
            description = description,
            title = title ?: "",
            thumbnail = thumbnail.toThumbnail()
        )
    }
}
