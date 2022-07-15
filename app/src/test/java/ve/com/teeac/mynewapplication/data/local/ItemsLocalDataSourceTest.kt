package ve.com.teeac.mynewapplication.data.local

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.MotherData
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.data.local.daos.ItemDao
import ve.com.teeac.mynewapplication.domain.models.TypeItem
import ve.com.teeac.mynewapplication.utils.Constants

@ExperimentalCoroutinesApi
class ItemsLocalDataSourceTest{

    @MockK
    lateinit var dao: ItemDao

    private lateinit var local: ItemsLocalDataSource

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        local = ItemsLocalDataSource(dao, TypeItem.COMICS.name)
    }

    @Test
    fun `should return list items by character id and increment the offset`(){
        val idCharacter = 1
        val list = listOf(MotherData.oneItem, MotherData.twoItem)
        val offset = slot<Int>()
        coEvery {
            dao.getByCharacterId(idCharacter, TypeItem.COMICS.name, capture(offset))
        } coAnswers {
            assertEquals(0, offset.captured)
            list
        }coAndThen {
            assertEquals(Constants.ITEMS_LIMIT.toInt(), offset.captured)
            emptyList()
        }coAndThen {
            assertEquals(Constants.ITEMS_LIMIT.toInt(), offset.captured)
            emptyList()
        }

        runTest {
            local.getItemsByCharacterId(idCharacter)
            local.getItemsByCharacterId(idCharacter)
            local.getItemsByCharacterId(idCharacter)
        }

        coVerify { dao.getByCharacterId(1, TypeItem.COMICS.name, offset = any()) }
        confirmVerified(dao)
    }

    @Test
    fun `should refresh page and delete db items`(){
        val idCharacter = 1
        val list = listOf(MotherData.oneItem, MotherData.twoItem)
        val offset = slot<Int>()
        coEvery {
            dao.getByCharacterId(idCharacter, TypeItem.COMICS.name, capture(offset))
        } coAnswers {
            assertEquals(0, offset.captured)
            list
        }coAndThen {
            assertEquals(Constants.ITEMS_LIMIT.toInt(), offset.captured)
            list
        }coAndThen {
            assertEquals(0, offset.captured)
            list
        }
        coEvery { dao.deleteAll() } returns Unit
        coEvery { dao.insert(list) } returns Unit

        runTest {
            local.getItemsByCharacterId(idCharacter)
            local.getItemsByCharacterId(idCharacter)
            local.refreshItems(list)
            local.getItemsByCharacterId(idCharacter)
        }

        coVerify { dao.getByCharacterId(1, TypeItem.COMICS.name, offset = any()) }
        coVerify { dao.deleteAll() }
        coVerify { dao.insert(list) }
        confirmVerified(dao)
    }
}