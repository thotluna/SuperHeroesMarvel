package ve.com.teeac.mynewapplication.data.local

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
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.dtos.DtoLocalRequestHandler
import ve.com.teeac.mynewapplication.data.dtos.ThumbnailDto
import ve.com.teeac.mynewapplication.data.local.daos.CharacterDao
import ve.com.teeac.mynewapplication.data.local.daos.LocalRequestHandlerDao

@ExperimentalCoroutinesApi
class CharactersLocalDataSourceTest {

    @MockK
    lateinit var dao: CharacterDao

    @MockK
    lateinit var daoHandlerDao: LocalRequestHandlerDao

    private lateinit var dataSource: CharactersLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = CharactersLocalDataSource(dao, daoHandlerDao)
    }

    @Test
    fun `should return character and don't call handler`() {

        coEvery { dao.getCharacterById(1) } coAnswers {
            CharacterDto(1, "character1", "description 1", ThumbnailDto("", ""))
        }

        runTest {
            dataSource.getCharacter(1)
        }

        coVerify(exactly = 1) { dao.getCharacterById(1) }
        coVerify(exactly = 0) { daoHandlerDao.getHandler() }

        confirmVerified(dao, daoHandlerDao)

    }

    @Test
    fun `should save new characters and don't call handler`() {
        val character = CharacterDto(1, "character1", "description 1", ThumbnailDto("", ""))
        coEvery { dao.insertAll(listOf(character)) } returns Unit

        runTest {
            dataSource.insertCharacters(listOf(character))
        }

        coVerify(exactly = 1) { dao.insertAll(listOf(character)) }
        coVerify(exactly = 0) { daoHandlerDao.getHandler() }

        confirmVerified(dao, daoHandlerDao)
    }

    @Test
    fun `should restart page and save new list characters`(){
        val character = CharacterDto(1, "character1", "description 1", ThumbnailDto("", ""))
        coEvery { dao.deleteAll() } returns Unit
        coEvery { dao.insertAll(listOf(character)) } returns Unit
        coEvery { daoHandlerDao.insert(DtoLocalRequestHandler()) }

        runTest {
            dataSource.refreshCharacters(listOf(character))
        }

        coVerify(exactly = 1) { dao.deleteAll() }
        coVerify(exactly = 1) { dao.insertAll(listOf(character)) }
        coVerify(exactly = 1) { daoHandlerDao.insert(DtoLocalRequestHandler()) }

        confirmVerified(dao, daoHandlerDao)
    }

    @Test
    fun `should return list character and increment page`(){
        coEvery { daoHandlerDao.getHandler() } returns DtoLocalRequestHandler()
        val list = listOf(CharacterDto(1, "character1", "description 1", ThumbnailDto("", "")))
        coEvery { dao.getAllCharacter(offset = 0) } coAnswers { list }
        coEvery { daoHandlerDao.insert(DtoLocalRequestHandler(1,1)) } returns Unit

        runTest {
            dataSource.getCharacters()
        }

        coVerify(exactly = 1) { daoHandlerDao.getHandler() }
        coVerify(exactly = 1) { dao.getAllCharacter(offset = 0) }
        coVerify(exactly = 1) { daoHandlerDao.insert(DtoLocalRequestHandler(1,1)) }

        confirmVerified(dao, daoHandlerDao)
    }

    @Test
    fun `should return list character with name start but not change page`(){
        coEvery { daoHandlerDao.getHandler() } returns DtoLocalRequestHandler()
        val list = listOf(CharacterDto(1, "character1", "description 1", ThumbnailDto("", "")))
        coEvery { dao.getAllCharacter(offset = 0, nameStartsWith = "cha") } coAnswers { list }

        runTest {
            dataSource.getCharacters()
        }

        coVerify(exactly = 1) { daoHandlerDao.getHandler() }
        coVerify(exactly = 1) { dao.getAllCharacter(offset = 0, nameStartsWith = "cha") }
        coVerify(exactly = 0) { daoHandlerDao.insert(DtoLocalRequestHandler(1,1)) }

        confirmVerified(dao, daoHandlerDao)
    }
}