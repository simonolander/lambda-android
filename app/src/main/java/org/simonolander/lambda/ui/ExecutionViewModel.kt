package org.simonolander.lambda.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.engine.Reduction
import org.simonolander.lambda.engine.reduceOnce

data class ExecutingTestCase(
    val testCase: TestCase,
    val reductions: List<Reduction> = emptyList(),
    val state: State = State.PENDING,
) {
    fun withReduction(reduction: Reduction): ExecutingTestCase {
        return ExecutingTestCase(
            testCase,
            reductions + reduction,
            state,
        )
    }

    fun withState(state: State): ExecutingTestCase {
        return ExecutingTestCase(
            testCase,
            reductions,
            state,
        )
    }

    enum class State {
        RUNNING,
        SUCCEEDED,
        FAILED,
        PENDING,
    }
}

class ExecutionViewModel(
    val exercise: Exercise,
    val solution: Expression,
) : ViewModel() {
    var executingTestCases by mutableStateOf(exercise.testCases.map { ExecutingTestCase(it) })
        private set

    var state by mutableStateOf(State.PAUSED)
        private set

    private val library = mapOf(
        exercise.functionName to solution
    )

    fun step() {
        state = State.PAUSED
        if (!stepOnce()) {
            state = State.TERMINATED
        }
    }

    fun pause() {
        state = State.PAUSED
    }

    fun play() {
        state = State.RUNNING
        viewModelScope.launch {
            while (state == State.RUNNING) {
                if (!stepOnce()) {
                    state = State.TERMINATED
                    break
                }
                delay(1000)
            }
        }
    }

    @Synchronized
    private fun stepOnce(): Boolean {
        val executingTestCases = executingTestCases.toMutableList()
        val index = executingTestCases.indexOfFirst {
            when (it.state) {
                ExecutingTestCase.State.PENDING -> true
                ExecutingTestCase.State.RUNNING -> true
                ExecutingTestCase.State.SUCCEEDED -> false
                ExecutingTestCase.State.FAILED -> false
            }
        }

        if (index == -1) {
            return false
        }

        val testCaseToStep = executingTestCases[index]
        val testCaseAfterStep = when (testCaseToStep.state) {
            ExecutingTestCase.State.PENDING -> testCaseToStep.withState(ExecutingTestCase.State.RUNNING)
            ExecutingTestCase.State.RUNNING -> {
                val currentExpression = testCaseToStep.reductions.lastOrNull()?.after
                    ?: testCaseToStep.testCase.input
                val reduction = reduceOnce(currentExpression, library)
                if (reduction != null) {
                    testCaseToStep.withReduction(reduction)
                }
                else {
                    val nextState =
                        if (currentExpression.alphaEquals(testCaseToStep.testCase.output)) {
                            ExecutingTestCase.State.SUCCEEDED
                        }
                        else {
                            ExecutingTestCase.State.FAILED
                        }
                    testCaseToStep.withState(nextState)
                }
            }
            ExecutingTestCase.State.FAILED, ExecutingTestCase.State.SUCCEEDED -> throw IllegalStateException()
        }
        executingTestCases[index] = testCaseAfterStep
        this.executingTestCases = executingTestCases.toList()
        return true
    }

    enum class State {
        PAUSED,
        RUNNING,
        TERMINATED,
    }
}
