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
import kotlin.math.floor

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
    buildFrame()
    checking()
}

val lightCellColor = Color(222, 184, 135, 255)
private fun getColor(column: Int, startColor: Color): Color {
    var oldColor = startColor
    if (startColor == Color.Black && column != 7) {
        oldColor = lightCellColor
    } else if (startColor == lightCellColor && column != 7) {
        oldColor = Color.Black
    }
    return oldColor
}

const val fieldSize = 64

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun grid() {
    var count = -1
    var oldColor = Color.Black
    LazyVerticalGrid(
        cells = GridCells.Fixed(8),
        modifier = Modifier
            .padding(horizontal = 19.dp, vertical = 19.dp)
    ) {
        items(fieldSize) {
            oldColor = getColor(column = count % 8, oldColor)
            count++
            Card(
                modifier = Modifier
                    .height(70.dp),
                backgroundColor = oldColor,
                shape = RectangleShape
            )
            {}
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
fun menu(updateRestart: (Boolean) -> Unit) {
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
                    updateRestart(true)
                    expanded = false
                })
            )
        }
    }
}

@Composable
fun setCh(
    updateOpen: (Boolean) -> Unit,
    updateText: (String) -> Unit,
    restart: Boolean,
    updateRestart: (Boolean) -> Unit
) {
    val turnWhite = mutableStateOf(listOf(Turn.Black))
    val location = mutableStateOf(makeLocation())
    val activeCh = mutableSetOf<Coordinates>()
    val delete = mutableSetOf<Coordinates>()
    val listOfQueen = mutableSetOf<Coordinates>()
    if (restart) updateRestart(false)
    for (elem in listOfOccupiedCells) {
        buildCheckers(
            elem,
            turnWhite,
            activeCh,
            location,
            delete,
            listOfQueen,
            updateOpen,
            updateText,
            restart,
            updateRestart)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun checking() {
    val restart = remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf("") }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text(text = text.value, fontSize = 22.sp, textAlign = TextAlign.Center) },
            buttons = {
                Button(
                    onClick = {
                        openDialog.value = false
                        restart.value = true
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
    if (restart.value) {
        setCh(
            updateOpen = { newCount -> openDialog.value = newCount },
            updateText = { newText -> text.value = newText },
            restart = restart.value,
            updateRestart = { newRes -> restart.value = newRes }
        )
    } else {
        setCh(
            updateOpen = { newCount -> openDialog.value = newCount },
            updateText = { newText -> text.value = newText },
            restart = restart.value,
            updateRestart = { newRes -> restart.value = newRes }
        )
    }
    menu(updateRestart = { newRes -> restart.value = newRes })
}

@Composable
fun buildCheckers(
    cord: Coordinates, turnWhite: MutableState<List<Turn>>, actives: MutableSet<Coordinates>,
    location: MutableState<Map<Coordinates, Coordinates>>, d: MutableSet<Coordinates>, listOfQ: MutableSet<Coordinates>,
    updateOpen: (Boolean) -> Unit, updateText: (String) -> Unit, restart: Boolean, updateRestart: (Boolean) -> Unit
): List<String> {
    val white = if (listOfOccupiedCells.indexOf(cord) < 12) Turn.White else Turn.Black
    val delete by remember { mutableStateOf(d) }
    if (!delete.contains(cord)) {
        var active = Coordinates.No
        val listOfQueen by remember { mutableStateOf(listOfQ) }
        val activeCh by remember { mutableStateOf(actives) }
        var queen by remember { mutableStateOf(false) }
        val result by remember { mutableStateOf(listOf<String>()) }
        var xOffset by remember { mutableStateOf(Cell(cord).centerX) }
        var yOffset by remember { mutableStateOf(Cell(cord).centerY) }
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
            .background(if (cord in activeCh) Color.Green else Color.Transparent)
            .pointerInput(Unit) {
                var oldCordX = xOffset
                var oldCordY = yOffset
                detectDragGestures(
                    onDragStart = {
                        list = allowedCells(
                            getCoordinates(Pair(xOffset, yOffset)),
                            white,
                            location.value,
                            queen
                        )
                    },
                    onDrag = { _, distance ->
                        if (turnWhite.value.first() == white || cord in activeCh) {
                            xOffset += distance.x
                            yOffset += distance.y
                            focused = true
                        }
                    },
                    onDragEnd = {
                        if (turnWhite.value.first() == white || cord in activeCh) {
                            focused = false
                            val coordinate = magnet(xOffset, yOffset)
                            if (coordinate in list) {
                                xOffset = Cell(coordinate).centerX
                                yOffset = Cell(coordinate).centerY
                                val newCord = getCoordinates(Pair(xOffset, yOffset))
                                val oldCord = getCoordinates(Pair(oldCordX, oldCordY))
                                location.value = step(newCord, oldCord, cord, location.value)
                                delete += eatenChecker(newCord, oldCord, location.value, white, queen).first
                                location.value = removeEaten(newCord, oldCord, location.value, white, queen)
                                active = if (activeCh.contains(cord)) {
                                    cord
                                } else Coordinates.No
                                if (!hasDeleted(
                                        allowedCells(newCord, white, location.value, queen),
                                        newCord
                                    )
                                ) activeCh.clear()
                                else {
                                    activeCh.clear()
                                    if (active != Coordinates.No) {
                                        activeCh.add(active)
                                        active = newCord
                                    }
                                }
                                activeCh.addAll(
                                    getActiveCh(
                                        turnWhite.value,
                                        activeCh,
                                        active,
                                        queen,
                                        listOfQueen,
                                        location.value
                                    )
                                )
                                turnWhite.value = changeTurn(activeCh, turnWhite.value, cord)
                                if (!queen) {
                                    if (isQueen(white, getCoordinates(Pair(xOffset, yOffset)))) {
                                        listOfQueen.add(cord)
                                    }
                                    queen = isQueen(white, getCoordinates(Pair(xOffset, yOffset)))
                                }
                                checkGameOver(location.value, listOfQueen, updateOpen, updateText)
                                if (restart) {
                                    location.value = makeLocation()
                                    listOfQueen.clear()
                                    activeCh.clear()
                                    delete.clear()
                                    turnWhite.value = mutableListOf(Turn.Black)
                                    updateRestart(true)
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
        val y = ((posY - 20) / 70).roundToInt()
        val x = if (y % 2 != 0) ((posX - 20) / 70 / 2).roundToInt() else floor((posX - 20) / 70 / 2).toInt()
        if (y * 4 + x in 0..31) return setOfAllCoordinates.keys.toList()[y * 4 + x]
    }
    return Coordinates.No
}

fun getCoordinates(cord: Pair<Double, Double>): Coordinates {
    val y = (cord.second / 70).roundToInt()
    val x = if (y % 2 != 0) (cord.first / 70 / 2).roundToInt() else floor(cord.first / 70 / 2).toInt()
    return setOfAllCoordinates.keys.toList()[y * 4 + x]
}