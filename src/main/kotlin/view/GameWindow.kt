package view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Direction
import model.Game


@OptIn(ExperimentalMaterialApi::class)
class GameWindow(private val game: Game) {
    private val sizes = listOf(245.dp, 185.dp, 147.dp, 123.dp)
    private val isWinOpen = mutableStateOf(true)
    private val isLossOpen = mutableStateOf(true)
    private val isWin = mutableStateOf(game.hasWon())
    private val currentValue = show.value

    @Composable
    fun Board() {

        if (direction.value == null) game.initialize()
        else if (!isWin.value && !game.hasLost() && game.canMove(direction.value!!)) {
            game.processMove(direction.value!!)
            direction.value = Direction.STATIC

        }
        if (game.hasWon() && isWinOpen.value) AlertDialog(
            onDismissRequest = { isWinOpen.value = false },
            title = { Text("THE WINNER!\n", fontSize = 50.sp, textAlign = TextAlign.Center) },
            text = { Text("Congratulations!\nYou've won!", fontSize = 45.sp, textAlign = TextAlign.Center) },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    AllertButton("Continue", Color.Green) {
                        isWin.value = false
                        isWinOpen.value = false
                    }
                    AllertButton("THAT'S ENOUGH", Color.Red) {
                        show.value = 0
                        direction.value = null
                    }
                }
            }
        )
        if (game.hasLost() && isLossOpen.value) AlertDialog(
            onDismissRequest = { },
            title = { Text("GAME OVER!\n", fontSize = 50.sp, textAlign = TextAlign.Center) },
            text = { Text("Meritoriously! Try again?", fontSize = 45.sp, textAlign = TextAlign.Center) },
            buttons = {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                    AllertButton("RESTART", Color.Green) {
                        show.value = 0
                        direction.value = null
                        show.value = currentValue
                    }
                    AllertButton("  EXIT  ", Color.Red) {
                        show.value = 0
                        direction.value = null
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
                    items(show.value) { i ->
                        LazyRow {
                            items(show.value) { j ->
                                Card(
                                    modifier = Modifier.height(sizes[show.value - 3])
                                        .width(sizes[show.value - 3] + 9.dp),
                                    backgroundColor = CellColor(game[i + 1, j + 1]),
                                    border = BorderStroke(3.dp, Color.DarkGray)
                                ) { CellText(game[i + 1, j + 1]) }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun CellText(num: Int?) {
        val size = if (num != null) {
            when (num.toString().count()) {
                in 1..2 -> (600 / show.value).sp
                3 -> (450 / show.value).sp
                4 -> (350 / show.value).sp
                else -> (250 / show.value).sp
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
    fun AllertButton(text: String, color: Color, action: (Unit) -> Unit) =
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
}



