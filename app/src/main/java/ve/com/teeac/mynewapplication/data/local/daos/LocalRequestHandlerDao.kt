package ve.com.teeac.mynewapplication.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ve.com.teeac.mynewapplication.data.dtos.DtoLocalRequestHandler

@Dao
interface LocalRequestHandlerDao {

    @Query("SELECT * FROM dto_local WHERE id = :id")
    suspend fun getHandler(id: Int = 1): DtoLocalRequestHandler?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dto: DtoLocalRequestHandler)
}