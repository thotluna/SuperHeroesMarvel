package ve.com.teeac.mynewapplication.data.dtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dto_local")
data class DtoLocalRequestHandler(
    @PrimaryKey
    val id: Int = 1,
    val page: Int = 0
)
