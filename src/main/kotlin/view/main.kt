package view

import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import model.*

val show = mutableStateOf(0)
val direction: MutableState<Direction?> = mutableStateOf(null)

@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {

    if (show.value == 0) Window(
        onCloseRequest = ::exitApplication,
        title = "2048 Compose",
        state = rememberWindowState(width = 800.dp, height = 800.dp)
    ) { InitialWindow().InitialView() }
    if (show.value != 0) Window(
        onCloseRequest = ::exitApplication,
        title = "2048 Compose",
        state = rememberWindowState(width = 800.dp, height = 800.dp),
        onKeyEvent = {
            when {
                (it.type == KeyEventType.KeyUp && it.key == Key.W) -> {
                    direction.value = Direction.UP
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.S) -> {
                    direction.value = Direction.DOWN
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.A) -> {
                    direction.value = Direction.LEFT
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.D) -> {
                    direction.value = Direction.RIGHT
                    true
                }
                else -> false
            }
        }
    ) {
        val currentValue = show.value
        MenuBar {
            Menu("Game") {
                Item("Restart",
                    onClick = {
                        show.value = 0
                        show.value = currentValue
                        direction.value = null
                    },
                    shortcut = KeyShortcut(Key.R, ctrl = true))
                Item("Start window", onClick = {
                    show.value = 0
                    direction.value = null
                })
                Item("Exit application", onClick = { exitApplication() })
            }
            Menu("Game Mode") {
                Item("3 x 3", onClick = {
                    show.value = 0
                    show.value = 3
                    direction.value = null
                })
                Item("4 x 4", onClick = {
                    show.value = 0
                    show.value = 4
                    direction.value = null
                })
                Item("5 x 5", onClick = {
                    show.value = 0
                    show.value = 5
                    direction.value = null
                })
                Item("6 x 6", onClick = {
                    show.value = 0
                    show.value = 6
                    direction.value = null
                })
            }
        }
        GameWindow(GameLogic(show.value, Random), direction).Board()
    }
}