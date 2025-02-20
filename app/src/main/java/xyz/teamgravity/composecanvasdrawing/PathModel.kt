package xyz.teamgravity.composecanvasdrawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.ImmutableList

data class PathModel(
    val id: String,
    val color: Color,
    val path: ImmutableList<Offset>
)