package ve.com.teeac.mynewapplication.data.local

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ve.com.teeac.mynewapplication.data.MotherData
import ve.com.teeac.mynewapplication.data.dtos.CharacterDto
import ve.com.teeac.mynewapplication.data.local.daos.CharacterDao

@ExperimentalCoroutinesApi
class CharactersLocalDataSourceTest {

    @MockK
    lateinit var dao: CharacterDao

    private lateinit var dataSource: CharactersLocalDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        dataSource = CharactersLocalDataSource(dao)
    }

    @Test
    fun `should return character by id`() {

        val character = MotherData.oneCharacter
        val slot = slot<Int>()
        coEvery { dao.getCharacterById(capture(slot)) } coAnswers {
            assertEquals(character.id, slot.captured)
            character
        }

        runTest {
            dataSource.getCharacter(character.id)
        }

        coVerify(exactly = 1) { dao.getCharacterById(any()) }

        confirmVerified(dao)

    }

    @Test
    fun `should restart page and save new list characters`() {
        firstCallDb()
        val list = MotherData.listCharacter
        val slot = slot<List<CharacterDto>>()
        coEvery { dao.deleteAll() } returns Unit
        coEvery { dao.insertAll(capture(slot)) } coAnswers {
            assertEquals(list, slot.captured)
        }

        runTest {
            dataSource.refreshCharacters(list)
        }

        coVerify(exactly = 1) { dao.deleteAll() }
        coVerify(exactly = 1) { dao.insertAll(any()) }

        confirmVerified(dao)
    }

    @Test
    fun `should subtract one page`(){

        val list = MotherData.listCharacter
        val slot = slot<Int>()
        coEvery { dao.getAllCharacter(capture(slot)) } coAnswers {
            assertEquals(0, slot.captured)
            list
        }coAndThen {
            assertEquals(0, slot.captured)
            list
        }

        runTest {
            dataSource.getCharacters()
            dataSource.reverseLastPage()
            dataSource.getCharacters()
        }

        coVerify(exactly = 2) { dao.getAllCharacter(any()) }

        confirmVerified(dao)
    }

    private fun firstCallDb() =runTest {
        val list = MotherData.listCharacter
        coEvery { dao.getAllCharacter(offset = 0) } coAnswers {
            list
        }
        dataSource.getCharacters()

        coVerify(exactly = 1) { dao.getAllCharacter(offset = 0) }

        confirmVerified(dao)
    }

    @Test
    fun `should return list character with name start but not change page`() {
        val list = MotherData.listCharacter
        val slotOffset = slot<Int>()
        coEvery {
            dao.getAllCharacter(
                offset = capture(slotOffset),
                nameStartsWith = "first"
            )
        } coAnswers {
            assertEquals(0, slotOffset.captured)
            list
        }
        coEvery {
            dao.getAllCharacter(
                offset = capture(slotOffset),
                nameStartsWith = "second"
            )
        } coAnswers {
            assertEquals(0, slotOffset.captured)
            list
        }

        runTest {
            dataSource.getCharacters("first")
            dataSource.getCharacters("second")
        }

        coVerify(exactly = 2) { dao.getAllCharacter(offset = any(), nameStartsWith = any()) }
        confirmVerified(dao)
    }

    @Test
    fun `should return list character without name start but change page`() {
        val list = MotherData.listCharacter
        val slotOffset = slot<Int>()
        coEvery { dao.getAllCharacter(offset = capture(slotOffset)) } coAnswers {
            assertEquals(slotOffset.captured, 0)
            list
        } coAndThen {
            assertEquals(slotOffset.captured, 21)
            list
        }

        runTest {
            dataSource.getCharacters()
            dataSource.getCharacters()
        }

        coVerify(exactly = 2) { dao.getAllCharacter(offset = any()) }
        confirmVerified(dao)
    }
}