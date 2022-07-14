package ve.com.teeac.mynewapplication.data.local

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.data.local.daos.ItemDao
import ve.com.teeac.mynewapplication.domain.models.TypeItem

@ExperimentalCoroutinesApi
class ItemsLocalDataSourceTest{

    @MockK
    lateinit var dao: ItemDao

    private lateinit var local: ItemsLocalDataSource

    private val items = listOf(
        ItemDto(
            id = 1,
            idCharacter = 1,
            title = "title 1",
            description = "description 1 ",
            thumbnail = ThumbnailDto("path 1", "extension 1 ")
        ),

        ItemDto(
            id = 2,
            idCharacter = 1,
            title = "title 2",
            description = "description 2 ",
            thumbnail = ThumbnailDto("path 2", "extension 2 ")
        ),
    )

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        local = ItemsLocalDataSource(dao, TypeItem.COMICS.name)
    }

    @Test
    fun `should insert new items`(){
        coEvery { dao.insert(items) } returns Unit

        runTest {
            local.insert(items)
        }

        coVerify { dao.insert(items) }
        confirmVerified(dao)
    }

    @Test
    fun `should get all items`(){
        coEvery { dao.getByCharacterId(1, TypeItem.COMICS.name, 0) } returns items

        runTest {
            val result = local.getItemsByCharacterId(1)
            assertEquals(items, result)
        }

        coVerify { dao.getByCharacterId(1, TypeItem.COMICS.name, offset = 0) }
        confirmVerified(dao)
    }

    @Test
    fun `should get empty list`(){
        coEvery { dao.getByCharacterId(2, TypeItem.COMICS.name, 0) } returns emptyList()

        runTest {
            local.getItemsByCharacterId(2)
        }

        coVerify { dao.getByCharacterId(2, TypeItem.COMICS.name, offset = 0) }
        confirmVerified(dao)
    }


}