package ve.com.teeac.mynewapplication.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ve.com.teeac.mynewapplication.data.dtos.DtoRemoteRequestHandler

@Dao
interface RemoteRequestHandlerDao {

    @Query("SELECT * FROM dto_handler WHERE id = :id LIMIT 1")
    suspend fun getHandler(id: Int = 1): DtoRemoteRequestHandler?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dto: DtoRemoteRequestHandler)
}