package que.sera.sera.todo.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import que.sera.sera.todo.repository.pref.PreferencesRepository
import que.sera.sera.todo.repository.pref.PreferencesRepositoryImpl
import que.sera.sera.todo.repository.todo.ToDoRepository
import que.sera.sera.todo.repository.todo.ToDoRepositoryFirebaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFireStore() = FirebaseFirestore.getInstance()

    @Singleton
    @Provides
    fun provideToDoRepository(
        db: FirebaseFirestore
    ): ToDoRepository =
        ToDoRepositoryFirebaseImpl(db)

    @Singleton
    @Provides
    fun provideUserPrefDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = {
            context.preferencesDataStoreFile("settings")
        }
    )

    @Singleton
    @Provides
    fun providePreferencesRepository(
        dataStore: DataStore<Preferences>
    ): PreferencesRepository =
        PreferencesRepositoryImpl(dataStore = dataStore)
}