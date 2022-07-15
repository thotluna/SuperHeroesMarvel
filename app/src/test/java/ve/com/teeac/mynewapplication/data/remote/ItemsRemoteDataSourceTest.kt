package ve.com.teeac.mynewapplication.data.remote

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.MotherData
import ve.com.teeac.mynewapplication.data.dtos.DataContainer
import ve.com.teeac.mynewapplication.data.dtos.DataWrapper
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.domain.models.TypeItem
import ve.com.teeac.mynewapplication.utils.Constants

@ExperimentalCoroutinesApi
class ItemsRemoteDataSourceTest {

    @MockK
    lateinit var api: ApiService

    private lateinit var remoteComic: ItemsRemoteDataSource
    private lateinit var remoteEvent: ItemsRemoteDataSource
    private lateinit var remoteSeries: ItemsRemoteDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        remoteComic = ItemsRemoteDataSource(api, TypeItem.COMICS.name)
        remoteEvent = ItemsRemoteDataSource(api, TypeItem.EVENTS.name)
        remoteSeries = ItemsRemoteDataSource(api, TypeItem.SERIES.name)
    }

    @Test
    fun `should return all comics without forceUpdate by id Character`() {
        val dataWrapper = MotherData.dataWrapperItem
        var page = 0
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(page), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper
        page++
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(page), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper
        page++
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(page), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper
        page++
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(page), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper

        runTest {
            remoteComic.getItemsByCharacterId(1)
            remoteComic.getItemsByCharacterId(1)
            remoteComic.getItemsByCharacterId(1)
            remoteComic.getItemsByCharacterId(1)
            val list = remoteComic.getItemsByCharacterId(1)
            Assert.assertEquals(emptyList<ItemDto>(), list)
        }

        coVerify { api.getComicByCharacterId(id = 1, offset = any()) }

        confirmVerified(api)
    }

    @Test
    fun `should return all events without forceUpdate by id Character`() {
        val dataWrapper = MotherData.dataWrapperItem
        coEvery {
            api.getEventsByCharacterId(id = 1, offset = getOffset(0))
        } coAnswers { dataWrapper }


        runTest {
            remoteEvent.getItemsByCharacterId(1)
        }

        coVerify { api.getEventsByCharacterId(id = 1, offset = any()) }
        confirmVerified(api)
    }

    @Test
    fun `should return all series without forceUpdate by id Character`() {
        val dataWrapper = MotherData.dataWrapperItem
        val page = 0
        coEvery {
            api.getSeriesByCharacterId(id = 1, offset = getOffset(page), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper


        runTest {
            remoteSeries.getItemsByCharacterId(1)
        }

        coVerify { api.getSeriesByCharacterId(id = 1, offset = any()) }

        confirmVerified(api)
    }

    @Test
    fun `should return all comics after forceUpdate by id Character`() {
        val dataWrapper = MotherData.dataWrapperItem
          coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(0), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(1), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper
        coEvery {
            api.getComicByCharacterId(id = 1, offset = getOffset(0), limit = Constants.ITEMS_LIMIT, orderBy = "title")
        } returns dataWrapper


        runTest {
            remoteComic.getItemsByCharacterId(1)
            remoteComic.getItemsByCharacterId(1)
            remoteComic.getItemsByCharacterId(1, true)
        }

        coVerify (exactly = 2){ api.getComicByCharacterId(id = 1, offset = getOffset(0)) }
        coVerify (exactly = 1) { api.getComicByCharacterId(id = 1, offset = getOffset(1)) }

        confirmVerified(api)
    }


    private fun getOffset(page: Int): String{
        return (page*Constants.ITEMS_LIMIT.toInt()).toString()
    }

//    @Test
//    fun `should return all items with forceUpdate`(){
//        coEvery { api.getComicByCharacterId(id = 1, offset = "0") } returns DataWrapper(
//            DataContainer(results = items, total = 2, count = 2)
//        )
//
//        runTest {
//            remote.getItemsByCharacterId(1)
//            remote.getItemsByCharacterId(1, forceUpdate = true)
//        }
//
//        coVerify(exactly = 2) { api.getComicByCharacterId(id = 1, offset = "0") }
//
//        confirmVerified(api)
//    }
}