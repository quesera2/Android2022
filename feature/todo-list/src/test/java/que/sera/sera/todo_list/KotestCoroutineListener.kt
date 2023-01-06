package que.sera.sera.todo_list

import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestScope
import io.kotest.core.test.testCoroutineScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class KotestCoroutineListener : BeforeSpecListener, AfterSpecListener {
    override suspend fun beforeSpec(spec: Spec) {
        Dispatchers.setMain(StandardTestDispatcher())
        spec.coroutineTestScope = true
    }

    override suspend fun afterSpec(spec: Spec) {
        Dispatchers.resetMain()
    }

    companion object {

        @OptIn(ExperimentalStdlibApi::class)
        fun TestScope.advanceUntilIdle() {
            testCoroutineScheduler.advanceUntilIdle()
        }
    }
}
