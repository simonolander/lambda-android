package org.simonolander.lambda.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class EventTypeTest : FunSpec({
    val events = EventType.values().toList()
    test("every event has a unique id") {
        events.forEach { event ->
            events.count { it.id == event.id } shouldBe 1
        }
    }
})
