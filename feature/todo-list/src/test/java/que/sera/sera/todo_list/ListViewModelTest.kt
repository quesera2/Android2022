package que.sera.sera.todo_list

import io.kotest.core.spec.style.FunSpec
import io.kotest.core.test.testCoroutineScheduler
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import que.sera.sera.todo.repository.pref.PreferencesRepository
import que.sera.sera.todo.repository.todo.ToDoRepository

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalStdlibApi::class)
class ListViewModelTest : FunSpec({
    lateinit var target: ListViewModel
    lateinit var toDoRepository: ToDoRepository
    lateinit var prefRepository: PreferencesRepository

    lateinit var testDispatcher: TestDispatcher

    beforeEach {
        toDoRepository = mockk()
        prefRepository = mockk {
            every { showCompletedTask } returns flowOf(false)
        }
        target = ListViewModel(
            toDoRepository = toDoRepository,
            prefRepository = prefRepository,
        )
    }

    afterEach {
        clearAllMocks()
    }

    beforeSpec {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)
    }

    afterSpec {
        Dispatchers.resetMain()
        unmockkAll()
    }

    context("完了したタスクの表示を切り替える") {
        beforeEach {
            coEvery { prefRepository.updateShowCompletedTask(any()) } just Runs
        }

        test("完了したタスクの表示を切り替える") {
            target.updateShowCompleteTask(true)
            testDispatcher.scheduler.advanceUntilIdle()
            coVerify { prefRepository.updateShowCompletedTask(true) }
        }
    }
})
