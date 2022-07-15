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
import ve.com.teeac.mynewapplication.data.local.CharactersLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.CharactersRemoteDataSource
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository

@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest {

    @MockK
    lateinit var local: CharactersLocalDataSource

    @MockK
    lateinit var remote: CharactersRemoteDataSource

    private lateinit var repository: CharactersRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = CharactersRepositoryImpl(local, remote)
    }

    @Test
    fun `should return data from local`() {
        val listCharacterDto = MotherData.fullListCharacter
        coEvery { local.getCharacters(nameStart = null) } coAnswers { listCharacterDto }

        runTest {
            val list = repository.getCharacters()
            assertEquals(listCharacterDto.map { it.toCharacterItem() }, list)
        }

        coVerify { local.getCharacters(nameStart = null) }
        coVerify(exactly = 0) { remote.getCharacters() }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return data from remote`() {
        val listCharacterDto = MotherData.fullListCharacter
        val filteredList = listCharacterDto.filter { !it.thumbnail.path.contains("image_") }
        coEvery { local.getCharacters(nameStart = null) } coAnswers {
            emptyList()
        } coAndThen {
            filteredList
        }
        coEvery {
            remote.getCharacters(
                nameStart = null,
                forceUpdate = false
            )
        } coAnswers { listCharacterDto }
        coEvery { local.insertCharacters(any()) } returns Unit


        runTest {
            val list = repository.getCharacters()
            val expected = filteredList.map { it.toCharacterItem() }
            assertEquals(expected.size, list.size)
            assertEquals(expected, list)
        }

        coVerify { local.getCharacters(nameStart = null) }
        coVerify { remote.getCharacters() }
        coVerify { local.insertCharacters(any()) }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return emptyList but don't have more any character in remote`() {
        val listCharacterDto = MotherData.fullListCharacter
        val filteredList = listCharacterDto.filter { !it.thumbnail.path.contains("image_") }
        coEvery {
            local.getCharacters(nameStart = null)
        } coAnswers {
            MotherData.listCharacter
        } coAndThen {
            filteredList
        } coAndThen {
            emptyList()
        }
        coEvery { local.reverseLastPage() } returns Unit
        coEvery {
            remote.getCharacters(
                nameStart = null,
                forceUpdate = false
            )
        } coAnswers {
            listCharacterDto
        } coAndThen {
            emptyList()
        }
        coEvery { local.insertCharacters(any()) } returns Unit


        runTest {
            repository.getCharacters()
            val list = repository.getCharacters()
            assertEquals(list.size, 0)
        }

        coVerify(exactly = 3) { local.getCharacters(nameStart = null) }
        coVerify(exactly = 1) { local.reverseLastPage() }
        coVerify(exactly = 2) { remote.getCharacters() }
        coVerify(exactly = 1) { local.insertCharacters(any()) }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return data by force`() {
        val listCharacterDto = MotherData.fullListCharacter
        val filteredList = listCharacterDto.filter { !it.thumbnail.path.contains("image_") }
        coEvery { remote.getCharacters(forceUpdate = true) } coAnswers {
            MotherData.fullListCharacter
        }
        coEvery {
            local.refreshCharacters(any())
        } returns Unit
        coEvery {
            local.getCharacters(nameStart = null)
        } coAnswers {
            filteredList
        }

        runTest {
            repository.getCharacters(forceUpdate = true)
        }

        coVerify(exactly = 1) { local.getCharacters(nameStart = null) }
        coVerify(exactly = 1) { local.refreshCharacters(any()) }
        coVerify(exactly = 1) { remote.getCharacters(forceUpdate = true) }

        confirmVerified(local, remote)

    }

    @Test
    fun `should return data by id from local`() {
        val listCharacterDto = MotherData.oneCharacter
        coEvery {
            local.getCharacter(id = 1)
        } coAnswers {
            listCharacterDto
        }

        runTest {
            repository.getCharacterById(id = 1)
        }

        coVerify(exactly = 1) { local.getCharacter(1) }
        coVerify(exactly = 0) { remote.getCharacterById(1) }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return data by id from remote`() {
        val listCharacterDto = MotherData.oneCharacter
        coEvery {
            local.getCharacter(id = 1)
        } coAnswers {
            null
        }
        coEvery { local.insertCharacters(listOf(listCharacterDto)) } returns Unit
        coEvery {
            remote.getCharacterById(id = 1)
        } coAnswers {
            listCharacterDto
        }

        runTest {
            repository.getCharacterById(id = 1)
        }

        coVerify(exactly = 1) { local.getCharacter(1) }
        coVerify(exactly = 1) { remote.getCharacterById(1) }
        coVerify(exactly = 1) { local.insertCharacters(any()) }

        confirmVerified(local, remote)
    }

}