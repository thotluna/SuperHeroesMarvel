package ve.com.teeac.mynewapplication.data.remote

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.data.local.daos.RemoteRequestHandlerDao

@ExperimentalCoroutinesApi
class CharactersRemoteDataSourceTest {

    @MockK
    lateinit var apiService: ApiService

    @MockK
    lateinit var local: RemoteRequestHandlerDao

    private lateinit var remote: CharactersRemoteDataSource

    private val dtoInitial = DtoRemoteRequestHandler(1, 1, 0, 3)

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remote = CharactersRemoteDataSource(apiService, local)
    }

    @Test
    fun `should increment the page`() {
        val dtoInitial = DtoRemoteRequestHandler()
        val dto = slot<DtoRemoteRequestHandler>()

        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharacters() } coAnswers { getFirstDataWrapper() }
        coEvery {local.insert(capture(dto))} coAnswers {
            assertEquals(dtoInitial.page + 1, dto.captured.page)
        }

        runTest {
            remote.getCharacters()
        }

        coVerify(exactly = 1) { local.getHandler() }
        coVerify(exactly = 1) { local.insert(any()) }

    }

    @Test
    fun `should return the empty list where totalApi == recordsObtained`() {
        coEvery { local.getHandler() } coAnswers { DtoRemoteRequestHandler(1, 0, 100, 100)}

        runTest {
            val list = remote.getCharacters()
            assertEquals(0, list.size)
        }

        coVerify(exactly = 1) { local.getHandler(id = 1) }
        coVerify (exactly = 0){ apiService.getCharacters() }
        coVerify(exactly = 0){local.insert(any())}

        confirmVerified(local, apiService)
    }

    private fun initialize()=runTest {

        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharacters() } coAnswers { getFirstDataWrapper() }
        coEvery { local.insert(DtoRemoteRequestHandler(1, 2, 1, 3)) } returns Unit
        remote.getCharacters()

        coVerify(exactly = 1) { local.getHandler() }
        coVerify(exactly = 1) { local.insert(any()) }
        coVerify(exactly = 1) { apiService.getCharacters() }
        confirmVerified(local, apiService)
    }


    @Test
    fun `should restart property and db with forceUpdate`(){

        initialize()
        coEvery { local.insert(DtoRemoteRequestHandler()) } returns Unit
        coEvery { apiService.getCharacters() } coAnswers { getFirstDataWrapper() }
        coEvery { local.insert(DtoRemoteRequestHandler(1, 1, 1, 3)) } returns Unit

        runTest {
            remote.getCharacters(forceUpdate = true)
        }

        coVerify(exactly = 1) { local.insert(DtoRemoteRequestHandler()) }
        coVerify(exactly = 1) { local.insert(DtoRemoteRequestHandler(1, 1, 1, 3)) }
        coVerify(exactly = 2) { apiService.getCharacters() }
        confirmVerified(local, apiService)
    }

    @Test
    fun `should not restart property and db with forceUpdate and nameStart`(){
        coEvery { local.getHandler(1) } coAnswers { dtoInitial }
        coEvery { apiService.getCharactersByStartName(nameStartsWith = "super") } coAnswers { getFirstDataWrapper() }

        runTest {
            remote.getCharacters(forceUpdate = true, nameStart = "super")
        }

        coVerify (exactly = 1){ local.getHandler() }
        coVerify(exactly = 1) { apiService.getCharactersByStartName("super") }
        confirmVerified(local, apiService)
    }

    @Test
    fun `should filtered the character with path contains word 'image_' where is nameStart`(){
        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharactersByStartName("super") } coAnswers { getThirdDataWrapper() }

        runTest {
            val list = remote.getCharacters(nameStart = "super")
            assertEquals(0, list.size)
        }
        coVerify (exactly = 1){ local.getHandler() }
        coVerify(exactly = 1) { apiService.getCharactersByStartName("super") }
        confirmVerified(local, apiService)
    }

    @Test
    fun `should filtered the character with path contains word 'image_'`(){
        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharacters() } coAnswers { getThirdDataWrapper() }
        coEvery { local.insert(DtoRemoteRequestHandler(1, 2, 1, 3)) } returns Unit

        runTest {
            val list = remote.getCharacters()
            assertEquals(0, list.size)
        }

        coVerify(exactly = 1) { local.getHandler() }
        coVerify(exactly = 1) { local.insert(any()) }
        coVerify(exactly = 1) { apiService.getCharacters() }
        confirmVerified(local, apiService)
    }

    private fun getFirstDataWrapper(): DataWrapper<CharacterDto> {
        return DataWrapper(
            data = DataContainer(
                total = 3,
                count = 1,
                results = listOf(
                    CharacterDto(
                        id = 1,
                        name = "name 1",
                        description = "description 1",
                        thumbnail = ThumbnailDto("extension 1", "path 1")
                    ),
                )
            )
        )
    }

    private fun getThirdDataWrapper(): DataWrapper<CharacterDto> {
        return DataWrapper(
            data = DataContainer(
                total = 3,
                count = 1,
                results = listOf(
                    CharacterDto(
                        id = 2,
                        name = "name 2",
                        description = "description 2",
                        thumbnail = ThumbnailDto("extension 2", "path 2 with image_url")
                    ),
                )
            )
        )
    }


}