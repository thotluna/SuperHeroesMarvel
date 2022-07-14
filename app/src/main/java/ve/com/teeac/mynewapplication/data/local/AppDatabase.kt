package ve.com.teeac.mynewapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.dtos.DtoLocalRequestHandler
import ve.com.teeac.mynewapplication.data.dtos.DtoRemoteRequestHandler
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.local.daos.CharacterDao
import ve.com.teeac.mynewapplication.data.local.daos.ItemDao
import ve.com.teeac.mynewapplication.data.local.daos.LocalRequestHandlerDao
import ve.com.teeac.mynewapplication.data.local.daos.RemoteRequestHandlerDao

@Database(
    entities = [
        CharacterDto::class,
        ItemDto::class,
        DtoLocalRequestHandler::class,
        DtoRemoteRequestHandler::class,], version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract val characterDao: CharacterDao
    abstract val itemsDao: ItemDao
    abstract val requestLocalHandlerDao: LocalRequestHandlerDao
    abstract val requestRemoteHandlerDao: RemoteRequestHandlerDao

    companion object {
        const val DATABASE_NAME = "marvel_data"
    }
}