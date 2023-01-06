package que.sera.sera.todo_list

import io.kotest.core.spec.style.FunSpec
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import que.sera.sera.todo.repository.pref.PreferencesRepository
import que.sera.sera.todo.repository.todo.ToDoRepository
import que.sera.sera.todo_list.KotestCoroutineListener.Companion.advanceUntilIdle

class ListViewModelTest : FunSpec({
    lateinit var target: ListViewModel
    lateinit var toDoRepository: ToDoRepository
    lateinit var prefRepository: PreferencesRepository

    extension(KotestCoroutineListener())
    extension(KotestMockkListener())

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

    context("完了したタスクの表示を切り替える") {
        beforeEach {
            coEvery { prefRepository.updateShowCompletedTask(any()) } just Runs
        }

        test("完了したタスクの表示を切り替える") {
            target.updateShowCompleteTask(true)
            advanceUntilIdle()
            coVerify { prefRepository.updateShowCompletedTask(true) }
        }
    }
})
