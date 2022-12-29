package que.sera.sera.todo.repository.pref

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    /** 完了したタスクを表示するかどうか */
    val showCompletedTask: Flow<Boolean>

    suspend fun updateShowCompletedTask(newValue: Boolean)
}