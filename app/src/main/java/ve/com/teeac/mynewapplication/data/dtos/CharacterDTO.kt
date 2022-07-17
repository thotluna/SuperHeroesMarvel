
package ve.com.teeac.mynewapplication.data.dtos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ve.com.teeac.mynewapplication.domain.models.Character
import ve.com.teeac.mynewapplication.domain.models.CharacterItem

@Entity(tableName = "character")
data class CharacterDto(
    @PrimaryKey
    val id: Int,
    val name: String,
    val description: String,
    @Embedded(prefix = "image_")
    val thumbnail: ThumbnailDto
) {
    fun toCharacter(): Character {
        return Character(
            id = id,
            name = name,
            description = description,
            thumbnail = thumbnail.toThumbnail()
        )
    }

    fun toCharacterItem(): CharacterItem {
        return CharacterItem(
            id = id,
            name = name,
            thumbnail = thumbnail.toThumbnail()
        )
    }
}
