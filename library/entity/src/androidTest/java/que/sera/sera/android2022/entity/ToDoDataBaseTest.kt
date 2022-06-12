package que.sera.sera.android2022.entity

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import que.sera.sera.android2022.entity.room.AppDatabase
import que.sera.sera.android2022.entity.room.ToDoDao
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ToDoDataBaseTest {
    private lateinit var db: AppDatabase
    private lateinit var todoDao: ToDoDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        todoDao = db.toDoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeUserAndReadInList() = runBlocking {
        val todo = ToDo(name = "テスト")
        todoDao.insert(todo)
        val expect = todoDao.getAll().first().first()
        assertThat(expect.id, equalTo(1))
        assertThat(expect.name, equalTo(todo.name))
        assertThat(expect.updated, equalTo(todo.updated))
        assertThat(expect.created, equalTo(todo.created))
    }
}
