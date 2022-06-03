package que.sera.sera.android2022.data.repository.pref

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesRepository {

    companion object Key {
        private val SHOW_COMPLETED_TASK = booleanPreferencesKey("show-completed-task")
    }

    override val showCompletedTask: Flow<Boolean>
        get() = dataStore
            .data
            .map {
                it[SHOW_COMPLETED_TASK] ?: true
            }

    override suspend fun updateShowCompletedTask(newValue: Boolean) {
        dataStore.edit {
            it[SHOW_COMPLETED_TASK] = newValue
        }
    }
}