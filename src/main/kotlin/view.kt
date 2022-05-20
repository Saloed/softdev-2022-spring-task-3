import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlin.math.roundToInt
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "English Checkers",
        state = rememberWindowState(
            width = 614.dp,
            height = 637.dp,
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        resizable = false
    ) {
        MaterialTheme {
            comp()
        }
    }
}

@Composable
fun comp() {
    grid()
    if (restart.value) {
        setCh()
    } else {
        setCh()
    }
    buildFrame()
    menu()
    dialog()
}

val restart = mutableStateOf(false)
val lightCellColor = Color(222, 184, 135, 255)
var startColor = lightCellColor
const val fieldSize = 64

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun grid() {
    var count = -1
    LazyVerticalGrid(
        cells = GridCells.Fixed(8),
        modifier = Modifier
            .padding(horizontal = 19.dp, vertical = 19.dp)
    ) {
        items(fieldSize) {
            count++
            Card(
                modifier = Modifier
                    .height(70.dp),
                backgroundColor = getColor(column = count % 8),
                shape = RectangleShape
            ) {}
        }
    }
}

val listOfNumbers = listOf("8", "7", "6", "5", "4", "3", "2", "1")
val listOfLetters = listOf("A", "B", "C", "D", "E", "F", "G", "H")
@Composable
fun buildFrame() {
    LazyColumn(
        modifier = Modifier
            .background(Color(101, 67, 33, 255))
            .padding(top = 11.dp)
            .width(19.dp)
    ) {
        items(listOfNumbers) {
            Text(
                text = it,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 0.dp)
                    .padding(bottom = 20.dp, top = 31.dp),
                fontSize = 17.sp
            )
        }
    }
    LazyColumn(
        modifier = Modifier
            .offset(579.dp, 0.dp)
            .padding(top = 11.dp)
            .width(19.dp)
            .background(Color(101, 67, 33, 255))
    ) {
        items(listOfNumbers) {
            Text(
                text = it,
                fontSize = 17.sp,
                modifier = Modifier
                    .padding(horizontal = 4.dp, vertical = 0.dp)
                    .padding(top = 31.dp, bottom = 20.dp)
            )
        }
    }
    LazyRow(
        modifier = Modifier
            .background(Color(101, 67, 33, 255))
            .padding(horizontal = 22.dp)
    ) {
        items(listOfLetters) {
            Text(
                text = it,
                modifier = Modifier.padding(horizontal = 29.dp, vertical = 1.dp),
                fontSize = 15.sp
            )
        }
    }
    LazyRow(
        modifier = Modifier
            .offset(0.dp, 579.dp)
            .background(Color(101, 67, 33, 255))
            .padding(horizontal = 22.dp)
    ) {
        items(listOfLetters) {
            Text(
                text = it,
                modifier = Modifier.padding(horizontal = 29.dp, vertical = 1.dp),
                fontSize = 15.sp
            )
        }
    }
}

@Composable
fun menu() {
    var expanded by remember { mutableStateOf(false) }
    Box {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier.size(20.dp)
        ) {
            Icon(Icons.Default.Menu, contentDescription = "Показать меню")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(lightCellColor)
        ) {
            Text(
                "Начать заново",
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 10.dp).clickable(onClick = {
                    restart()
                    expanded = false
                })
            )
        }
    }
}

@Composable
fun setCh() {
    if (restart.value) restart.value = false
    for (elem in listOfOccupiedCells) {
        buildCheckers(elem, delete.value.contains(elem))
    }
}

val openDialog = mutableStateOf(false)
val text = mutableStateOf("")

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun dialog() {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = text.value, fontSize = 22.sp, textAlign = TextAlign.Center) },
            buttons = {
                Button(
                    onClick = {
                        openDialog.value = false
                        restart()
                    },
                    modifier = Modifier.padding(horizontal = 30.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(101, 67, 33, 255))
                ) {
                    Text("OK", fontSize = 22.sp, textAlign = TextAlign.Center)
                }
            },
            modifier = Modifier.background(Color.Gray),
            backgroundColor = Color.Gray
        )
    }
}

val turnWhite = mutableStateOf(turn)
val activeCh = mutableStateOf(setOf<Coordinates>())
val delete = mutableStateOf(mutableSetOf<Coordinates>())
val location = mutableStateOf(listOfOccupiedCells.toMutableList())
val listOfQueen = mutableStateOf(mutableSetOf<Coordinates>())

enum class Turn {
    White, Black, P
}

@Composable
fun buildCheckers(cord: Coordinates, del: Boolean): List<String> {
    val white = if (listOfOccupiedCells.indexOf(cord) < 12) Turn.White else Turn.Black
    if (!del) {
        var queen by remember { mutableStateOf(false) }
        val result by remember { mutableStateOf(listOf<String>()) }
        var xOffset by remember { mutableStateOf(Cell(cord).centerX) }
        var yOffset by remember { mutableStateOf(Cell(cord).centerY) }
        val x = resetCoordinates(cord, location.value)
        xOffset = x.first
        yOffset = x.second
        var focused by remember { mutableStateOf(false) }
        var list = setOf<Coordinates>()
        val img = if (white == Turn.White) {
            if (!queen) imageW else queenImageW
        } else {
            if (!queen) imageB else queenImageB
        }
        val image: Painter = painterResource(img)
        Image(painter = image, contentDescription = "checker", modifier = Modifier
            .offset { IntOffset(xOffset.roundToInt(), yOffset.roundToInt()) }
            .size(63.dp)
            .clip(shape = CircleShape)
            .zIndex(if (focused) 1f else 0f)
            .background(if (cord in activeCh.value) Color.Green else Color.Transparent)
            .pointerInput(Unit) {
                var oldCordX = xOffset
                var oldCordY = yOffset
                detectDragGestures(
                    onDragStart = {
                        list = allowedCells(getCoordinates(Pair(xOffset, yOffset)), white, location.value, queen)
                    },
                    onDrag = { _, distance ->
                        if (turnWhite.value[0] == white || cord in activeCh.value) {
                            xOffset += distance.x
                            yOffset += distance.y
                            focused = true
                        }
                    },
                    onDragEnd = {
                        if (turnWhite.value[0] == white || cord in activeCh.value) {
                            focused = false
                            val coordinate = magnet(xOffset, yOffset)
                            if (coordinate in list) {
                                xOffset = Cell(coordinate).centerX
                                yOffset = Cell(coordinate).centerY
                                update(
                                    getCoordinates(Pair(xOffset, yOffset)),
                                    queen,
                                    getCoordinates(Pair(oldCordX, oldCordY)),
                                    white,
                                    cord
                                )
                                if (!queen) {
                                    if (isQueen(white, getCoordinates(Pair(xOffset, yOffset)))) {
                                        listOfQueen.value.add(cord)
                                    }
                                    queen = isQueen(white, getCoordinates(Pair(xOffset, yOffset)))
                                }
                            } else {
                                xOffset = oldCordX
                                yOffset = oldCordY
                            }
                        }
                        oldCordX = xOffset
                        oldCordY = yOffset
                    }
                )
            }
        )
        return result
    }
    return listOf()
}

fun magnet(posX: Double, posY: Double): Coordinates {
    if (posX > 0 && posX < 530 && posY > 0 && posY < 540) {
        val x = listOfLetters1[((posX - 20) / 70).roundToInt()]
        val y = listOfNumbers1[((posY - 20) / 70).roundToInt()]
        val res = try {
            Coordinates.valueOf(x.toString() + y.toString())
        } catch (e: IllegalArgumentException) {
            Coordinates.No
        }
        return res
    }
    return Coordinates.No
}

fun getCoordinates(cord: Pair<Double, Double>): Coordinates {
    val x = listOfLetters1[(cord.first / 70).roundToInt()]
    val y = listOfNumbers1[(cord.second / 70).roundToInt()]
    return Coordinates.valueOf(x.toString() + y.toString())
}

fun resetCoordinates(cord: Coordinates, loc: List<Coordinates>): Pair<Double, Double> {
    val ind = listOfOccupiedCells.indexOf(cord)
    val x = Cell(loc[ind]).centerX
    val y = Cell(loc[ind]).centerY
    return Pair(x, y)
}

private fun getColor(column: Int): Color {
    val oldColor = startColor
    if (startColor == Color.Black && column != 7) {
        startColor = lightCellColor
    } else if (startColor == lightCellColor && column != 7) {
        startColor = Color.Black
    }
    return oldColor
}