package dev.berggren

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var hasFixedHeight by remember { mutableStateOf(false) }
            var boxVisibility by remember { mutableStateOf((0..5).map { true }) }

            MaterialTheme {
                Column(Modifier.padding(48.dp)) {
                    Row(
                        Modifier
                            .width(IntrinsicSize.Max)
                            .run {
                                if (hasFixedHeight) this.height(128.dp)
                                else this
                            }
                    ) {
                        boxVisibility.forEachIndexed { index, isVisible ->
                            ExpandableBox(isVisible = isVisible, index = index)
                            Spacer(Modifier.size(32.dp))
                        }
                    }
                    Spacer(Modifier.height(48.dp))
                    Column {
                        Row {
                            Checkbox(
                                hasFixedHeight,
                                onCheckedChange = { hasFixedHeight = it }
                            )
                            Spacer(Modifier.width(16.dp))
                            Text("Fixed height?")
                        }
                        boxVisibility.forEachIndexed { boxIndex, isVisible ->
                            Row {
                                Checkbox(
                                    checked = isVisible,
                                    onCheckedChange = {
                                        boxVisibility = boxVisibility.mapIndexed { index, oldIsVisible ->
                                            if (index == boxIndex) !oldIsVisible
                                            else oldIsVisible
                                        }
                                    }
                                )
                                Spacer(Modifier.width(16.dp))
                                Text("Box $boxIndex")
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun ExpandableBox(isVisible: Boolean, index: Int) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandHorizontally(),
        exit = shrinkHorizontally()
    ) {
        Box(
            Modifier
                .size(128.dp)
                .background(Color.Magenta),
            contentAlignment = Alignment.Center
        ) {
            Text("Box $index")
        }
    }
}
