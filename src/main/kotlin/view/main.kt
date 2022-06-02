package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import model.Direction
import java.io.File
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.input.key.*
import androidx.compose.ui.window.MenuBar
import model.GameLogic
import model.Random

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
fun main() = application {
    var  show by remember { mutableStateOf(0) }
    var direction: Direction? by remember {  mutableStateOf(null) }
    var showInitialWindow by remember { mutableStateOf(true) }
    var showGameWindow by remember { mutableStateOf(false) }
    var isWinOpen by remember { mutableStateOf(true) }
    val isLossOpen by remember { mutableStateOf(true) }
    val currentValue = show
    val sizes = listOf(245.dp, 185.dp, 147.dp, 123.dp)
    val game = GameLogic(show, Random)
    var isWin by remember { mutableStateOf(game.hasWon()) }

    if (showInitialWindow) Window(
        onCloseRequest = ::exitApplication,
        title = "2048 Compose",
        state = rememberWindowState(width = 800.dp, height = 800.dp)
    ) {
        Box {
            Image(
                painter = BitmapPainter(image = remember(File("src/main/resources/drawable/bg.jpg")) {
                    loadImageBitmap(File("src/main/resources/drawable/bg.jpg").inputStream()) }),
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize(),
                contentDescription = null
            )
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(10.dp)) {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = "2048",
                    style = TextStyle(
                        color = Color.DarkGray,
                        fontSize = 185.sp,
                        fontStyle = FontStyle.Italic,
                        letterSpacing = 10.sp,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(color = Color(250, 163, 245), blurRadius = 10f, offset = Offset(5f, 5f)),
                    )
                )
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Choose the field size",
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 50.sp,
                            letterSpacing = 5.sp,
                            textAlign = TextAlign.Center,
                            shadow = Shadow(color = Color(250, 163, 245), blurRadius = 5f, offset = Offset(5f, 5f))
                        )
                    )
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        ModeButton("3x3") {
                            show = 3
                            showInitialWindow = false
                            showGameWindow = true
                        }
                        ModeButton("4x4") {
                            show = 4
                            showInitialWindow = false
                            showGameWindow = true
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        ModeButton("5x5") {
                            show = 5
                            showInitialWindow = false
                            showGameWindow = true
                        }
                        ModeButton("6x6") {
                            show = 6
                            showInitialWindow = false
                            showGameWindow = true
                        }
                    }
                }
            }
        }
    }
    if (showGameWindow) Window(
        onCloseRequest = ::exitApplication,
        title = "2048 Compose",
        state = rememberWindowState(width = 800.dp, height = 800.dp),
        onKeyEvent = {
            when {
                (it.type == KeyEventType.KeyUp && it.key == Key.W) -> {
                    direction = Direction.UP
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.S) -> {
                     direction = Direction.DOWN
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.A) -> {
                     direction = Direction.LEFT
                    true
                }
                (it.type == KeyEventType.KeyUp && it.key == Key.D) -> {
                    direction = Direction.RIGHT
                    true
                }
                else -> false
            }
        }
    ) {
        fun reOpen() {
            showGameWindow = false
            showGameWindow = true
        }
        MenuBar {
            Menu("Game") {
                Item("Restart",
                    onClick = {
                        reOpen()
                        show = currentValue
                        direction = null
                        isWinOpen = true
                    },
                    shortcut = KeyShortcut(Key.R, ctrl = true)
                )
                Item("Start window", onClick = {
                    showGameWindow = false
                    showInitialWindow = true
                    direction = null
                    isWinOpen = true
                })
                Item("Exit application", onClick = { exitApplication() })
            }
            Menu("Game Mode") {
                Item("3 x 3", onClick = {
                    reOpen()
                    show = 3
                    direction = null
                    isWinOpen = true
                })
                Item("4 x 4", onClick = {
                    reOpen()
                    show = 4
                    direction = null
                    isWinOpen = true
                })
                Item("5 x 5", onClick = {
                    reOpen()
                    show = 5
                    direction = null
                    isWinOpen = true
                })
                Item("6 x 6", onClick = {
                    reOpen()
                    show = 6
                    direction = null
                    isWinOpen = true
                })
            }
        }

        if (direction == null) game.initialize()
        else if (!isWin && !game.hasLost() && game.canMove(direction!!)) {
            game.processMove(direction!!)
            direction = Direction.STATIC
        }
        if (game.hasWon() && isWinOpen) AlertDialog(
            onDismissRequest = { isWinOpen = false },
            title = { Text("THE WINNER!\n", fontSize = 50.sp, textAlign = TextAlign.Center) },
            text = { Text("Congratulations!\nYou've won!", fontSize = 45.sp, textAlign = TextAlign.Center) },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    AlertButton("Continue", Color.Green) {
                        isWin = false
                        isWinOpen = false
                    }
                    AlertButton("THAT'S ENOUGH", Color.Red) {
                        showGameWindow = false
                        showInitialWindow = true
                        direction = null
                    }
                }
            }
        )
        if (game.hasLost() && isLossOpen) AlertDialog(
            onDismissRequest = { },
            title = { Text("GAME OVER!\n", fontSize = 50.sp, textAlign = TextAlign.Center) },
            text = { Text("Meritoriously! Try again?", fontSize = 45.sp, textAlign = TextAlign.Center) },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    AlertButton("RESTART", Color.Green) {
                        showGameWindow = false
                        showGameWindow = true
                        direction = null
                        show = currentValue
                        isWinOpen = true
                    }
                    AlertButton("  EXIT  ", Color.Red) {
                        isWin = false
                        showGameWindow = false
                        showInitialWindow = true
                        direction = null
                        isWinOpen = true
                    }
                }
            }
        )
        Box(
            modifier = Modifier.fillMaxSize().background(Color.DarkGray),
            contentAlignment = Alignment.Center,
        ) {
            MaterialTheme {
                LazyColumn {
                    items(show) { i ->
                        LazyRow {
                            items(show) { j ->
                                Card(
                                    modifier = Modifier.height(sizes[show - 3])
                                        .width(sizes[show - 3] + 9.dp),
                                    backgroundColor = CellColor(game[i + 1, j + 1]),
                                    border = BorderStroke(3.dp, Color.DarkGray)
                                ) { CellText(game[i + 1, j + 1], show) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ModeButton(text: String, action: (Unit) -> Unit) {
    Button(
        modifier = Modifier.size(180.dp),
        onClick = { action(Unit) },
        shape = RoundedCornerShape(50.dp),
        border = BorderStroke(5.dp, Color.DarkGray),
        colors = ButtonDefaults.buttonColors(backgroundColor = Color(237, 197, 245))
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 65.sp,
                letterSpacing = 4.sp,
                shadow = Shadow(color = Color.White, blurRadius = 5f, offset = Offset(5f, 5f))
            )
        )
    }
}

@Composable
private fun CellText(num: Int?, show: Int) {
    val size = if (num != null) {
        when (num.toString().count()) {
            in 1..2 -> (600 / show).sp
            3 -> (450 / show).sp
            4 -> (350 / show).sp
            else -> (250 / show).sp
        }
    } else 10.sp
    Text(
        text = if (num == null) "" else "$num",
        style = TextStyle(
            textAlign = TextAlign.Center,
            fontSize = size,
            color = Color.White,
            shadow = Shadow(Color(132, 123, 123), blurRadius = 10f, offset = Offset(5f, 5f))
        )
    )
}

@Composable
private fun CellColor(num: Int?): Color {
    return when (num) {
        null -> Color(216, 214, 214)
        2 -> Color(240, 204, 244)
        4 -> Color(183, 123, 196)
        8 -> Color(216, 216, 103)
        16 -> Color(216, 112, 103)
        32 -> Color(87, 214, 232)
        64 -> Color(112, 207, 117)
        128 -> Color(181, 245, 156)
        256 -> Color(253, 100, 251)
        512 -> Color(119, 238, 134)
        1024 -> Color(142, 136, 221)
        2048 -> Color.Yellow
        4096 -> Color(244, 0, 0)
        8192 -> Color(100, 62, 255)
        16384 -> Color(96, 147, 122)
        else -> Color.Black
    }
}

@Composable
private fun AlertButton(text: String, color: Color, action: (Unit) -> Unit) =
    Button(
        onClick = { action(Unit) },
        shape = AbsoluteRoundedCornerShape(3.dp),
        border = BorderStroke(5.dp, Color.DarkGray),
        colors = ButtonDefaults.buttonColors(backgroundColor = color)
    ) {
        Text(
            text = text,
            style = TextStyle(
                color = Color.Black,
                fontSize = 22.sp,
                shadow = Shadow(color = Color.White, blurRadius = 5f, offset = Offset(5f, 5f))
            )
        )
    }