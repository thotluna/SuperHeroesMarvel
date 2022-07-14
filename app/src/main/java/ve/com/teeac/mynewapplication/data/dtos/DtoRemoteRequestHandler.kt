package ve.com.teeac.mynewapplication.data.dtos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dto_handler")
data class DtoRemoteRequestHandler(
    @PrimaryKey
    val id: Int = 1,
    val page: Int = 0,
    val recordsObtained: Int = 0,
    val totalRecordsApi: Int = 0
)
