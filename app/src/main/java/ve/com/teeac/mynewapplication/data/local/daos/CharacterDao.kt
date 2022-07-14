package ve.com.teeac.mynewapplication.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.utils.Constants

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character ORDER By name LIMIT ${Constants.CHARACTERS_LIMIT_LOCAL} OFFSET :offset " )
    suspend fun getAllCharacter(offset: Int = 0): List<CharacterDto>

    @Query("SELECT * FROM character WHERE name like :nameStartsWith || '%' LIMIT ${Constants.CHARACTERS_LIMIT_LOCAL} OFFSET :offset" )
    suspend fun getAllCharacter(offset: Int = 0, nameStartsWith: String = ""): List<CharacterDto>

    @Query("SELECT * FROM character WHERE id= :id")
    suspend fun getCharacterById(id: Int): CharacterDto?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharacterDto>)

    @Query("DELETE FROM character")
    suspend fun deleteAll()

}