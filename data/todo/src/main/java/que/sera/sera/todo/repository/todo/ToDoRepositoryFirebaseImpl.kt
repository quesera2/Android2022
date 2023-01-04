package que.sera.sera.todo.repository.todo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await
import que.sera.sera.todo.entity.ToDo
import javax.inject.Inject

internal class ToDoRepositoryFirebaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ToDoRepository {

    companion object {
        private const val COLLECTION_PATH_TODO = "todo"

        private const val FIELD_ID = "id"
        private const val FIELD_STATUS = "status"
        private const val FIELD_UPDATED = "updated"

        private const val STATUS_INCOMPLETE = "Incomplete"
    }

    override fun fetchToDos(
        showComplete: Boolean
    ): Flow<List<ToDo>> = callbackFlow {
        val collection = fireStore.collection(COLLECTION_PATH_TODO)
        val query = if (showComplete) {
            collection.whereEqualTo(FIELD_STATUS, STATUS_INCOMPLETE)
                .orderBy(FIELD_UPDATED, Query.Direction.DESCENDING)
        } else {
            collection.orderBy(FIELD_STATUS, Query.Direction.DESCENDING)
                .orderBy(FIELD_UPDATED, Query.Direction.DESCENDING)
        }
        val callback = query.addSnapshotListener { value, error ->
            when {
                value != null -> value.documents
                    .mapNotNull { it.toObject(ToDo::class.java) }
                    .let { trySend(it) }

                error != null -> close(error)
            }
        }
        awaitClose {
            callback.remove()
        }
    }.distinctUntilChanged()

    override suspend fun getToDo(
        id: Int
    ): ToDo? = fireStore.collection(COLLECTION_PATH_TODO)
        .whereEqualTo(FIELD_ID, id)
        .get()
        .await()
        .toObjects(ToDo::class.java)
        .firstOrNull()

    override suspend fun registerToDo(todo: ToDo) {
        val currentMaxValue = fireStore.collection(COLLECTION_PATH_TODO)
            .orderBy(FIELD_ID, Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .toObjects(ToDo::class.java)
            .firstNotNullOfOrNull { it.id }
            ?: 0

        fireStore.collection(COLLECTION_PATH_TODO)
            .add(todo.copy(id = currentMaxValue + 1))
            .await()
    }

    override suspend fun updateToDo(todo: ToDo) {
        fireStore.collection(COLLECTION_PATH_TODO)
            .whereEqualTo(FIELD_ID, todo.id)
            .get()
            .await()
            .documents
            .first()
            .reference
            .set(todo, SetOptions.merge())
            .await()
    }
}