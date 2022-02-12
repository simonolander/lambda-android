package org.simonolander.lambda.domain

/**
 * An event represents something that can happen inside the application.
 * When they occur, they trigger some action, like a dialog or an achievement.
 * Events should be recorded.
 */
enum class EventType(val id: String) {
    CompletedExercise(id = "completed-exercise"),
    ParseError(id = "parse-error"),
}
