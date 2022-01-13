package que.sera.sera.android2022.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import que.sera.sera.android2022.repository.todo.ToDoRepository
import que.sera.sera.android2022.repository.todo.ToDoRepositoryImpl
import que.sera.sera.android2022.room.AppDatabase
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
    fun provideToDoRepository(
        appDatabase: AppDatabase
    ): ToDoRepository = ToDoRepositoryImpl(appDatabase)
}