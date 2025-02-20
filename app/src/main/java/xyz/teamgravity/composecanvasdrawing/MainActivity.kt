package xyz.teamgravity.composecanvasdrawing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import xyz.teamgravity.composecanvasdrawing.ui.theme.ComposeCanvasDrawingTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeCanvasDrawingTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { padding ->
                    val viewmodel by viewModels<MainViewModel>()
                    val state by viewmodel.state.collectAsStateWithLifecycle();

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        CanvasDrawField(
                            paths = state.paths,
                            currentPath = state.currentPath,
                            onPathStart = viewmodel::onPathStart,
                            onPathEnd = viewmodel::onPathEnd,
                            onPathDraw = viewmodel::onPathDraw,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1F)
                        )
                        CanvasController(
                            selectedColor = state.selectedColor,
                            colors = ColorsProvider.VALUE,
                            onSelectColor = viewmodel::onSelectColor,
                            onClearCanvas = viewmodel::onClearCanvas
                        )
                    }
                }
            }
        }
    }
}