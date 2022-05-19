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
    for (elem in coordinatesB + coordinatesW) {
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
val activeCh = mutableStateOf(setOf<String>())
val delete = mutableStateOf(mutableSetOf<String>())
val location = mutableStateOf(listOfOccupiedCells)
@Composable
fun buildCheckers(cord: String, del: Boolean): List<String> {
    val white = if (cord in coordinatesW) "white" else "black"
    if (!del) {
        var queen by remember { mutableStateOf(false) }
        val result by remember { mutableStateOf(listOf<String>()) }
        var xOffset by remember { mutableStateOf(Cell(cord).centerX) }
        var yOffset by remember { mutableStateOf(Cell(cord).centerY) }
        val x = resetCoordinates(cord, location.value)
        var active = ""
        xOffset = x.first
        yOffset = x.second
        var focused by remember { mutableStateOf(false) }
        var list = setOf<String>()
        val img = if (white == "white") {
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
                        list = allowedCells(Pair(xOffset, yOffset), white, location.value, queen)
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
                            val coordinate = getCord(xOffset, yOffset)
                            if (allowedStep(coordinate, list)) {
                                xOffset = Cell(coordinate).centerX
                                yOffset = Cell(coordinate).centerY
                                val ind = location.value.indexOf(
                                    checkOccupiedCells(
                                        Pair(oldCordX, oldCordY),
                                        Pair(xOffset, yOffset),
                                        queen
                                    )[0]
                                )
                                location.value.add(
                                    ind,
                                    checkOccupiedCells(
                                        Pair(oldCordX, oldCordY),
                                        Pair(xOffset, yOffset),
                                        queen
                                    )[1]
                                )
                                location.value.remove(
                                    checkOccupiedCells(
                                        Pair(oldCordX, oldCordY),
                                        Pair(xOffset, yOffset),
                                        queen
                                    )[0]
                                )
                                var old = ""
                                var new = ""
                                if (!queen) {
                                    old = eat(oldCordX, oldCordY, xOffset, yOffset, location.value, white).first
                                    new = eat(oldCordX, oldCordY, xOffset, yOffset, location.value, white).second
                                } else {
                                    old = eatForQueen(oldCordX, oldCordY, xOffset, yOffset, location.value, white).first
                                    new = eatForQueen(oldCordX, oldCordY, xOffset, yOffset, location.value, white).second
                                }
                                val index = location.value.indexOf(new)
                                delete.value += old
                                if (index != -1) location.value.add(index, "")
                                location.value.remove(new)
                                val c = if (turnWhite.value.size > 1) turnWhite.value[1]
                                else turnWhite.value[0]
                                if (cord in activeCh.value) active = cord
                                activeCh.value = checkDelete(location.value, c, active, queen)
                                if (activeCh.value.isNotEmpty()) {
                                    if ("p" !in turnWhite.value) turnWhite.value.add(0, "p")
                                    if (cord !in activeCh.value) {
                                        if (turnWhite.value[1] == "white") turnWhite.value.add("black")
                                        if (turnWhite.value[1] == "black") turnWhite.value.add("white")
                                        turnWhite.value.removeAt(1)
                                    }
                                } else {
                                    active = ""
                                    if ("p" in turnWhite.value) turnWhite.value.remove("p")
                                    if (turnWhite.value[0] == "white") turnWhite.value.add("black")
                                    if (turnWhite.value[0] == "black") turnWhite.value.add("white")
                                    turnWhite.value.removeAt(0)
                                }
                                if (!queen) {
                                    if (isQueen(white, yOffset)) {
                                        val i =
                                            (coordinatesW + coordinatesB).indexOf(cord)
                                        val temp = location.value[ind]
                                        location.value[i] = temp + "q"
                                    }
                                    queen = isQueen(white, yOffset)
                                }
                                val t = gameOver(location.value)
                                if (t != "") {
                                    openDialog.value = true
                                    text.value = t
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

private fun getColor(column: Int): Color {
    val oldColor = startColor
    if (startColor == Color.Black && column != 7) {
        startColor = lightCellColor
    } else if (startColor == lightCellColor && column != 7) {
        startColor = Color.Black
    }
    return oldColor
}