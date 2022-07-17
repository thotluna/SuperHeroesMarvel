package ve.com.teeac.mynewapplication.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.utils.Constants

@Dao
interface ItemDao {

    @Query("SELECT * FROM items WHERE id_character = :id AND type = :type LIMIT ${Constants.ITEMS_LIMIT} OFFSET :offset")
    suspend fun getByCharacterId(id: Int, type: String, offset: Int): List<ItemDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dto: ItemDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(listDto: List<ItemDto>)

    @Query("DELETE FROM items")
    suspend fun deleteAll()
}
