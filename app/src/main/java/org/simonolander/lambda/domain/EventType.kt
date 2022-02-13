package org.simonolander.lambda.domain

/**
 * An event represents something that can happen inside the application.
 * When they occur, they trigger some action, like a dialog or an achievement.
 * Events should be recorded.
 */
enum class EventType(val id: String) {
    COMPLETED_EXERCISE("completed-exercise"),
    PARSE_ERROR("parse-error"),
    SUCCESSFUL_EXERCISE_SUBMISSION("successful-exercise-submission"),
}
