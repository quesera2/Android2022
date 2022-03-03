package que.sera.sera.android2022.repository.todo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.tasks.await
import que.sera.sera.android2022.model.todo.ToDo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ToDoRepositoryFirebaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ToDoRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getToDos(
        showComplete: Boolean
    ): Flow<List<ToDo>> = callbackFlow {
        val callback = fireStore.collection("todo")
            .orderBy("status", Query.Direction.DESCENDING)
            .orderBy("updated", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                value?.documents?.mapNotNull {
                    it.toObject(ToDo::class.java)
                }?.let {
                    trySend(it)
                }
                error?.let {
                    close(error)
                }
            }
        awaitClose {
            callback.remove()
        }
    }.distinctUntilChanged()

    override suspend fun getToDo(id: Int): ToDo? = suspendCoroutine { continuation ->
        fireStore.collection("todo")
            .whereEqualTo("id", id)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(it.result.toObjects(ToDo::class.java).first())
                } else {
                    continuation.resume(null)
                }
            }
    }

    override suspend fun registerToDo(todo: ToDo) {
        val currentMaxValue = fireStore.collection("todo")
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()
            .toObjects(ToDo::class.java)
            .firstOrNull()
            ?.id
            ?: 0

        fireStore.collection("todo")
            .add(todo.copy(id = currentMaxValue + 1))
            .await()
    }

    override suspend fun updateToDo(todo: ToDo) {
        fireStore.collection("todo")
            .whereEqualTo("id", todo.id)
            .get()
            .await()
            .documents
            .first()
            .reference
            .set(todo, SetOptions.merge())
            .await()
    }
}