package org.simonolander.lambda.ui.view

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
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
