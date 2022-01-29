package org.simonolander.lambda.ui.view

import androidx.compose.foundation.gestures.*
import androidx.compose.ui.Modifier

object NoFlingBehaviour : FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        return 0f
    }
}

fun Modifier.scrollableNoFling(
    state: ScrollableState,
    orientation: Orientation = Orientation.Vertical,
): Modifier {
    return scrollable(
        state = state,
        orientation = orientation,
        flingBehavior = NoFlingBehaviour
    )
}
