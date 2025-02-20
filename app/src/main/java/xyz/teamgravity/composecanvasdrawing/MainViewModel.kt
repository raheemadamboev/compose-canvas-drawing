package xyz.teamgravity.composecanvasdrawing

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.uuid.Uuid

class MainViewModel : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state: StateFlow<MainState> = _state.asStateFlow()

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onPathStart() {
        _state.update { state ->
            state.copy(
                currentPath = PathModel(
                    id = Uuid.random().toString(),
                    color = state.selectedColor,
                    path = persistentListOf()
                )
            )
        }
    }

    fun onPathEnd() {
        val currentPath = state.value.currentPath ?: return
        _state.update { state ->
            state.copy(
                currentPath = null,
                paths = (state.paths + currentPath).toImmutableList()
            )
        }
    }

    fun onPathDraw(position: Offset) {
        val currentPath = state.value.currentPath ?: return
        _state.update { state ->
            state.copy(
                currentPath = currentPath.copy(
                    path = (currentPath.path + position).toImmutableList()
                )
            )
        }
    }

    fun onSelectColor(color: Color) {
        _state.update { state ->
            state.copy(
                selectedColor = color
            )
        }
    }

    fun onClearCanvas() {
        _state.update { state ->
            state.copy(
                currentPath = null,
                paths = persistentListOf()
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // MISC
    ///////////////////////////////////////////////////////////////////////////

    data class MainState(
        val selectedColor: Color = Color.Black,
        val currentPath: PathModel? = null,
        val paths: ImmutableList<PathModel> = persistentListOf()
    )
}