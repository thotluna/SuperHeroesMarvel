package ve.com.teeac.mynewapplication.data.remote

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.MotherData
import ve.com.teeac.mynewapplication.data.dtos.*
import ve.com.teeac.mynewapplication.data.local.daos.RemoteRequestHandlerDao

@ExperimentalCoroutinesApi
class CharactersRemoteDataSourceTest {

    @MockK
    lateinit var apiService: ApiService

    @MockK
    lateinit var local: RemoteRequestHandlerDao

    private lateinit var remote: CharactersRemoteDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        remote = CharactersRemoteDataSource(apiService, local)
    }

    private fun initialize() = runTest {
        val dtoInitial = MotherData.dtoRemoteHandlerInitial
        val dataWrapper = MotherData.dataWrapper
        val dto = MotherData.dtoRemoteHandlerFirst
        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharacters() } coAnswers { dataWrapper }
        coEvery { local.insert(dto) } returns Unit

        remote.getCharacters()

        coVerify(exactly = 1) { local.getHandler() }
        coVerify(atMost = 2) { apiService.getCharacters() }
        coVerify(exactly = 1) { local.insert(any()) }
        confirmVerified(local, apiService)
    }

    @Test
    fun `should return initial filtered listCharacter after call getCharacter with forceUpdate`() {
        initialize()
        coEvery { local.insert(DtoRemoteRequestHandler()) } returns Unit
        coEvery { apiService.getCharacters(offset = "0") } coAnswers { MotherData.dataWrapper }
        coEvery { local.insert(MotherData.dtoRemoteHandlerFirst) } returns Unit

        runTest {
            val list = remote.getCharacters(forceUpdate = true)
            assertNotEquals(MotherData.listCharacter, list)
            assertEquals(MotherData.listCharacter.size - 1, list.size)
        }

        coVerify(exactly = 1) { local.insert(DtoRemoteRequestHandler()) }
        coVerify { apiService.getCharacters() }
        coVerify(exactly = 2) { local.insert(MotherData.dtoRemoteHandlerFirst) }
        confirmVerified(apiService, local)
    }

    @Test
    fun `should return filtered listCharacter after other call getCharacter without forceUpdate`() {
        initialize()
        val dtoInitialize = MotherData.dtoRemoteHandlerFirst
        val dataWrapper = MotherData.dataWrapper
        val dtoSecond = DtoRemoteRequestHandler(
            id = 1,
            page = dtoInitialize.page + 1,
            recordsObtained = dtoInitialize.recordsObtained + dataWrapper.data.count,
            totalRecordsApi = dataWrapper.data.total
        )
        coEvery { apiService.getCharacters(offset = "100") } coAnswers { dataWrapper }
        coEvery { local.insert(dtoSecond) } returns Unit

        runTest {
            val list = remote.getCharacters()
            assertNotEquals(MotherData.listCharacter, list)
            assertEquals(MotherData.listCharacter.size - 1, list.size)
        }

        coVerify { apiService.getCharacters(offset = "100") }
        coVerify { local.insert(dtoSecond) }
        confirmVerified(apiService, local)
    }

    @Test
    fun `should return filtered listCharacter by Name after other call getCharacter without forceUpdate`() {
        initialize()
        val dataWrapper = MotherData.dataWrapper
        coEvery {
            apiService.getCharactersByStartName(
                nameStartsWith = "cha",
                offset = "0",
            )
        } coAnswers { dataWrapper }

        runTest {
            val list = remote.getCharacters(nameStart = "cha", forceUpdate = true)
            assertNotEquals(MotherData.listCharacter, list)
            assertEquals(MotherData.listCharacter.size - 1, list.size)
        }

        coVerify { apiService.getCharactersByStartName(nameStartsWith = "cha", offset = "0") }
        confirmVerified(apiService)
    }

    @Test
    fun `should return an unfiltered character`() {
        val character = MotherData.characterWithoutThumbnail
        coEvery { apiService.getCharacterById(character.id) } returns DataWrapper(
            DataContainer(
                total = 1,
                count = 1,
                results = listOf(character)
            )
        )

        runTest {
            val result = remote.getCharacterById(character.id)
            assertEquals(character, result)
        }

        coVerify(exactly = 1) { apiService.getCharacterById(character.id) }
        confirmVerified(apiService)
    }

    @Test
    fun `should return emptyList after get all character from api `(){
        val dtoInitial = MotherData.dtoRemoteHandlerInitial
        val dataWrapper = DataWrapper(
            DataContainer(
                total = 3,
                count = 3,
                results = MotherData.listCharacter
            )
        )
        val dto = MotherData.dtoRemoteHandlerFirst.copy(recordsObtained = 3)
        coEvery { local.getHandler() } coAnswers { dtoInitial }
        coEvery { apiService.getCharacters() } coAnswers { dataWrapper }
        coEvery { local.insert(dto) } returns Unit


        runTest {
            remote.getCharacters()
            val list = remote.getCharacters()
            assertEquals(list, emptyList<CharacterDto>())
        }

        coVerify(exactly = 1) { local.getHandler() }
        coVerify(atMost = 1) { apiService.getCharacters() }
        coVerify(exactly = 1) { local.insert(any()) }
        confirmVerified(local, apiService)


    }
}