package que.sera.sera.android2022.repository.todo

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import que.sera.sera.android2022.model.todo.ToDo
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ToDoRepositoryFirebaseImpl @Inject constructor(
    private val fireStore: FirebaseFirestore
) : ToDoRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getToDos(
        showComplete: Boolean
    ): Flow<List<ToDo>> = callbackFlow {
        val callback = fireStore.collection("todo")
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
        val currentMaxValue = currentMaxValue()
        registerInner(todo = todo, currentMaxValue = currentMaxValue)
    }

    private suspend fun currentMaxValue() = suspendCoroutine<Int> { continuation ->
        fireStore.collection("todo")
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentValue = it.result.toObjects(ToDo::class.java).firstOrNull()?.id ?: 1
                    continuation.resume(currentValue)
                } else {
                    continuation.resumeWithException(it.exception!!)
                }
            }
    }

    private suspend fun registerInner(
        todo: ToDo,
        currentMaxValue: Int
    ) = suspendCoroutine<Unit> { continuation ->
        fireStore.collection("todo")
            .add(todo.copy(id = currentMaxValue + 1))
            .addOnSuccessListener {
                continuation.resume(Unit)
            }
            .addOnFailureListener {
                continuation.resumeWithException(it)
            }
    }

    override suspend fun updateToDo(todo: ToDo) {
        TODO("Not yet implemented")
    }
}