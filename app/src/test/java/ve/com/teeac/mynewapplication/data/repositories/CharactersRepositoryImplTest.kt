package ve.com.teeac.mynewapplication.data.repositories

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.data.local.CharactersLocalDataSource
import ve.com.teeac.mynewapplication.data.remote.CharactersRemoteDataSource
import ve.com.teeac.mynewapplication.domain.repositories.CharactersRepository

@ExperimentalCoroutinesApi
class CharactersRepositoryImplTest{

    @MockK
    lateinit var local: CharactersLocalDataSource

    @MockK
    lateinit var remote: CharactersRemoteDataSource

    private lateinit var repository: CharactersRepository

    private val characterDto = CharacterDto(1, "character1", "description 1", ThumbnailDto("", ""))

    @Before
    fun setup(){
        MockKAnnotations.init(this)
        repository = CharactersRepositoryImpl(local, remote)
    }

    @Test
    fun `should return list characterItem from local`(){
        coEvery { local.getCharacters() } returns listOf(characterDto)

        runTest {
            repository.getCharacters()
        }

        coVerify(exactly = 1) { local.getCharacters() }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return list characterItem from remote`(){
        coEvery { local.getCharacters() } returns emptyList() coAndThen { listOf(characterDto) }
        coEvery { remote.getCharacters() } returns listOf(characterDto)
        coEvery { local.insertCharacters(listOf(characterDto)) }

        runTest {
            repository.getCharacters()
        }

        coVerify(exactly = 2) { local.getCharacters() }
        coVerify(exactly = 1) { remote.getCharacters() }
        coVerify(exactly = 1) { local.insertCharacters(listOf(characterDto)) }

        confirmVerified(local, remote)
    }

    @Test
    fun `should return list characterItem with forceUpdate`(){
        coEvery { remote.getCharacters() } returns listOf(characterDto)
        coEvery { local.refreshCharacters(listOf(characterDto)) }
        coEvery { local.getCharacters() } returns listOf(characterDto)

        runTest {
            repository.getCharacters(forceUpdate = true)
        }

        coVerify(exactly = 1) { remote.getCharacters() }
        coVerify(exactly = 1) { local.refreshCharacters(listOf(characterDto)) }
        coVerify(exactly = 0) { local.insertCharacters(listOf(characterDto)) }
        coVerify(exactly = 1) { local.getCharacters() }

        confirmVerified(local, remote)
    }


}