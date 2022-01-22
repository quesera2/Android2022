package que.sera.sera.android2022

import io.mockk.*
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import que.sera.sera.android2022.model.todo.ToDo
import que.sera.sera.android2022.repository.todo.ToDoRepository
import que.sera.sera.android2022.repository.todo.ToDoRepositoryImpl
import que.sera.sera.android2022.room.ToDoDao

class ToDoRepositoryImplTest {

    lateinit var repository: ToDoRepository
    lateinit var dao: ToDoDao

    @Before
    fun setup() {
        dao = mockk()
        repository = ToDoRepositoryImpl(dao)
    }

    @Test
    fun testGetAll() {
        every { dao.getAll() } returns emptyFlow()
        repository.getToDos()
        verify(exactly = 1) { dao.getAll() }
        confirmVerified(dao)
    }

    @Test
    fun testRegisterToDo() = runBlocking {
        coEvery { dao.insert(any()) } returns Unit
        repository.registerToDo(ToDo())
        coVerify(exactly = 1) { dao.insert(any()) }
        confirmVerified(dao)
    }

    @Test
    fun testUpdateToDo() = runBlocking {
        coEvery { dao.update(any()) } returns Unit
        repository.updateToDo(ToDo())
        coVerify(exactly = 1) { dao.update(any()) }
        confirmVerified(dao)
    }

    @Test
    fun testGetTodo() = runBlocking {
        coEvery { dao.findById(12) } returns ToDo(12, "テスト")
        val actual = repository.getToDo(12)
        coVerify(exactly = 1) { dao.findById(12) }
        confirmVerified(dao)

        assertThat(actual, notNullValue())
        assertThat(actual!!.name, equalTo("テスト"))
        assertThat(actual.id, equalTo(12))
    }

}