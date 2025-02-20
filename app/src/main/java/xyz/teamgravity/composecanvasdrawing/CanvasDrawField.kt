package xyz.teamgravity.composecanvasdrawing

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.util.fastForEach
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.abs

@Composable
fun CanvasDrawField(
    paths: ImmutableList<PathModel>,
    currentPath: PathModel?,
    onPathStart: () -> Unit,
    onPathEnd: () -> Unit,
    onPathDraw: (position: Offset) -> Unit,
    modifier: Modifier = Modifier
) {
    Canvas(
        modifier = modifier
            .clipToBounds()
            .background(Color.White)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = {
                        onPathStart()
                    },
                    onDragEnd = {
                        onPathEnd()
                    },
                    onDragCancel = {
                        onPathEnd()
                    },
                    onDrag = { change, _ ->
                        onPathDraw(change.position)
                    }
                )
            }
    ) {
        paths.fastForEach { path ->
            drawPath(
                path = path.path,
                color = path.color
            )
        }
        currentPath?.let { path ->
            drawPath(
                path = path.path,
                color = path.color
            )
        }
    }
}

private fun DrawScope.drawPath(
    path: ImmutableList<Offset>,
    color: Color
) {
    val smoothedPath = Path().apply {
        if (path.isNotEmpty()) {
            moveTo(
                x = path.first().x,
                y = path.first().y
            )

            val smoothness = 5
            for (i in 1..path.lastIndex) {
                val from = path[i - 1]
                val to = path[i]
                val dx = abs(from.x - to.x)
                val dy = abs(from.y - to.y)
                if (dx >= smoothness || dy >= smoothness) {
                    quadraticTo(
                        x1 = (from.x + to.x) / 2F,
                        y1 = (from.y + to.y) / 2F,
                        x2 = to.x,
                        y2 = to.y
                    )
                }
            }
        }
    }
    drawPath(
        path = smoothedPath,
        color = color,
        style = Stroke(
            width = 10F,
            cap = StrokeCap.Round,
            join = StrokeJoin.Round
        )
    )
}