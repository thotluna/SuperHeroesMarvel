package ve.com.teeac.mynewapplication.data.remote

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.dtos.DataContainer
import ve.com.teeac.mynewapplication.data.dtos.DataWrapper
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.domain.models.TypeItem

@ExperimentalCoroutinesApi
class ItemsRemoteDataSourceTest{

    @MockK
    lateinit var api: ApiService

    private lateinit var remote: ItemsRemoteDataSource

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
    fun setUp() {
        MockKAnnotations.init(this)
        remote = ItemsRemoteDataSource(api, TypeItem.COMICS.name)
    }

    @Test
    fun `should return all items`(){
        coEvery { api.getComicByCharacterId(id = 1, offset = "0") } returns DataWrapper(
            DataContainer(results = items, total = 2, count = 2)
        )

        coEvery { api.getComicByCharacterId(id = 1, offset = "100") } returns DataWrapper(
            DataContainer(results = items, total = 2, count = 2)
        )

        runTest {
            remote.getItemsByCharacterId(1)
            remote.getItemsByCharacterId(2)
        }

        coVerify { api.getComicByCharacterId(id = 1, offset = "0") }
        coVerify { api.getComicByCharacterId(id = 1, offset = "100") }

        confirmVerified(api)
    }

    @Test
    fun `should return all items with forceUpdate`(){
        coEvery { api.getComicByCharacterId(id = 1, offset = "0") } returns DataWrapper(
            DataContainer(results = items, total = 2, count = 2)
        )

        runTest {
            remote.getItemsByCharacterId(1)
            remote.getItemsByCharacterId(1, forceUpdate = true)
        }

        coVerify(exactly = 2) { api.getComicByCharacterId(id = 1, offset = "0") }

        confirmVerified(api)
    }
}