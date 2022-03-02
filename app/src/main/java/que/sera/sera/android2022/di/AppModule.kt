package que.sera.sera.android2022.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import que.sera.sera.android2022.repository.pref.PreferencesRepository
import que.sera.sera.android2022.repository.pref.PreferencesRepositoryImpl
import que.sera.sera.android2022.repository.todo.ToDoRepository
import que.sera.sera.android2022.repository.todo.ToDoRepositoryFirebaseImpl
import que.sera.sera.android2022.repository.todo.ToDoRepositoryImpl
import que.sera.sera.android2022.room.AppDatabase
import que.sera.sera.android2022.room.ToDoDao
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, "app_db").build()

    @Singleton
    @Provides
    fun provideToDoDao(
        appDataBase: AppDatabase
    ): ToDoDao = appDataBase.toDoDao()

    @Singleton
    @Provides
    fun provideFireStore() = FirebaseFirestore.getInstance()

//    @Singleton
//    @Provides
//    fun provideToDoRepository(
//        dao: ToDoDao
//    ): ToDoRepository = ToDoRepositoryImpl(dao)


    @Singleton
    @Provides
    fun provideToDoRepository(
        db: FirebaseFirestore
    ): ToDoRepository = ToDoRepositoryFirebaseImpl(db)

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
    ): PreferencesRepository = PreferencesRepositoryImpl(dataStore)
}