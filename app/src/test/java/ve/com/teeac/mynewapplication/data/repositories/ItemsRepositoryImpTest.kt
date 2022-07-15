package ve.com.teeac.mynewapplication.data.repositories

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.MotherData
import ve.com.teeac.mynewapplication.data.dtos.ItemDto
import ve.com.teeac.mynewapplication.data.local.ItemsLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.ItemsRemoteDataSource
import ve.com.teeac.mynewapplication.domain.repositories.ItemsRepository

@ExperimentalCoroutinesApi
class ItemsRepositoryImpTest{

    @MockK
    lateinit var local: ItemsLocalDataSource

    @MockK
    lateinit var remote: ItemsRemoteDataSource

    private lateinit var repository: ItemsRepository

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        repository = ItemsRepositoryImp(local, remote)
    }

    @Test
    fun `should return itemList from local`(){
        val itemList = MotherData.listItem
        coEvery { local.getItemsByCharacterId(1) } coAnswers { itemList }
        runTest {
            repository.getItemsByCharacterId(id = 1, forceUpdate = false)
        }
        coVerify (exactly = 1){ local.getItemsByCharacterId(1) }
        coVerify (exactly = 0){ remote.getItemsByCharacterId(1) }
        confirmVerified(local, remote)
    }

    @Test
    fun `should return itemList from remote`(){
        val itemList = MotherData.listItem
        coEvery { local.getItemsByCharacterId(1) } coAnswers { emptyList() }
        coEvery { remote.getItemsByCharacterId(1) } coAnswers { itemList }
        coEvery { local.insert(any()) } returns Unit

        runTest {
            val list = repository.getItemsByCharacterId(id = 1, forceUpdate = false)
            assertEquals(itemList.map { it.toItem() }, list)
        }

        coVerify (exactly = 1){ local.getItemsByCharacterId(1) }
        coVerify (exactly = 1){ remote.getItemsByCharacterId(1) }
        coVerify (exactly = 1){ local.insert(any()) }
        confirmVerified(local, remote)
    }

    @Test
    fun `should return emptyList from remote`(){
         coEvery { local.getItemsByCharacterId(1) } coAnswers { emptyList() }
        coEvery { remote.getItemsByCharacterId(1) } coAnswers { emptyList() }

        runTest {
            val list = repository.getItemsByCharacterId(id = 1, forceUpdate = false)
            assertEquals(emptyList<ItemDto>(), list)
        }

        coVerify (exactly = 1){ local.getItemsByCharacterId(1) }
        coVerify (exactly = 1){ remote.getItemsByCharacterId(1) }
        confirmVerified(local, remote)
    }

    @Test
    fun `should return itemList from remote by forceUpdate`(){
        val itemList = MotherData.listItem
        coEvery { local.getItemsByCharacterId(1) } coAnswers { emptyList() }
        coEvery { remote.getItemsByCharacterId(1, forceUpdate = true) } coAnswers { itemList }
        coEvery { local.refreshItems(any()) } returns Unit

        runTest {
            val list = repository.getItemsByCharacterId(id = 1, forceUpdate = true)
            assertEquals(itemList.map { it.toItem() }, list)
        }

        coVerify (exactly = 1){ local.getItemsByCharacterId(1) }
        coVerify (exactly = 1){ remote.getItemsByCharacterId(1, forceUpdate = true) }
        coVerify (exactly = 1){ local.refreshItems(any()) }
        confirmVerified(local, remote)
    }


}