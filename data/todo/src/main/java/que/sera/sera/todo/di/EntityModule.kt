package que.sera.sera.todo.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import que.sera.sera.todo.entity.room.AppDatabase
import que.sera.sera.todo.entity.room.ToDoDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EntityModule {
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
}