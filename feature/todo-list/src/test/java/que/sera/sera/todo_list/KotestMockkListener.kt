package que.sera.sera.todo_list

import io.kotest.core.listeners.AfterEachListener
import io.kotest.core.listeners.AfterSpecListener
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.mockk.clearAllMocks
import io.mockk.unmockkAll

class KotestMockkListener : AfterEachListener, AfterSpecListener {

    override suspend fun afterEach(testCase: TestCase, result: TestResult) {
        clearAllMocks()
    }

    override suspend fun afterSpec(spec: Spec) {
        unmockkAll()
    }
}
