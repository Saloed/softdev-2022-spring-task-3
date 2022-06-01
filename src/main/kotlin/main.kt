import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.zIndex
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun app() {
    lazyVerticalGrid()
    frame()
    menu()
    fillingList()
    if (!restart.value) {
        checkers()
    } else checkers()
    longText()
    alertDialog()
}

fun main() = application {
    Window(
        resizable = false,
        title = "Russian Checkers",
        state = rememberWindowState(
            position = WindowPosition.Aligned(Alignment.Center),
            height = 979.dp,
            width = 956.dp
        ),
        onCloseRequest = ::exitApplication
    ) {
        app()
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun lazyVerticalGrid() {
    var column = 0
    LazyVerticalGrid(
        cells = GridCells.Fixed(8),
        modifier = Modifier.padding(30.dp, 30.dp)
    ) {
        items(8 * 8) {
            Card(
                shape = RectangleShape,
                modifier = Modifier
                    .height(110.dp),
                backgroundColor = color(column),
            ) {
                column++
            }
        }
    }
}


fun color(column: Int): Color {
    return if (floor(column / 8.0) % 2 == 0.0) {
        if (column % 2 == 0) Color(236, 213, 167, 255) // white
        else Color(53, 40, 30) // black
    } else {
        if (column % 2 == 0) Color(53, 40, 30)
        else Color(236, 213, 167, 255)
    }
}


@Composable
fun frame() {
    Canvas(modifier = Modifier.size(1000.dp, 30.dp).offset(0.dp, 910.dp)) {
        drawRect(
            color = Color(101, 67, 33),
        )
    }
    Canvas(modifier = Modifier.size(1000.dp, 30.dp).offset(0.dp, 0.dp)) {
        drawRect(
            color = Color(101, 67, 33),
        )
    }
    Canvas(modifier = Modifier.size(30.dp, 1000.dp).offset(0.dp, 0.dp)) {
        drawRect(
            color = Color(101, 67, 33),
        )
    }
    Canvas(modifier = Modifier.size(30.dp, 1000.dp).offset(910.dp, 0.dp)) {
        drawRect(
            color = Color(101, 67, 33),
        )
    }
}

val list = mutableListOf<Pair<Double, Double>>()
var delete = mutableListOf<Pair<Double, Double>>()
val actives = mutableListOf<Pair<Double, Double>>()
var turn = false
val queens = mutableListOf<Pair<Double, Double>>()

@Composable
fun counting(color: Color, coordinates: String, white: Boolean, element: Boolean): List<Pair<Double, Double>> {
    var offsetX by remember { mutableStateOf(Logic().cell(coordinates).first) }
    var offsetY by remember { mutableStateOf(Logic().cell(coordinates).second) }
    var offsetZ by remember { mutableStateOf(0f) }

    val activeList by remember { mutableStateOf(actives) }

    var queen by remember { mutableStateOf(false) }

    if (!element) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .width(93.dp).height(93.dp).clip(CircleShape)
                .background(activeBackground(coordinates, activeList, queen))
                .zIndex(offsetZ)


                .pointerInput(Unit) {
                    var initialPositionX = offsetX
                    var initialPositionY = offsetY

                    detectDragGestures(
                        onDragStart = {},

                        onDrag = { change, dragAmount ->

                            if (activeList.isEmpty() && turn == white || Logic().cell(coordinates) in activeList && turn == white) {
                                change.consumeAllChanges()
                                offsetZ = 1f
                                offsetX += dragAmount.x
                                offsetY += dragAmount.y
                            }
                        },

                        onDragEnd = {
                            if (activeList.isEmpty() && turn == white || Logic().cell(coordinates) in activeList && turn == white) {


                                offsetZ = 0f
                                val x = Logic().centreSearchX(offsetX, offsetY).first
                                val y = Logic().centreSearchX(offsetX, offsetY).second


                                    if (activeList.isEmpty()) {
                                        if ((x to y in Logic().stepsForCheckers1(initialPositionX to initialPositionY, white, queen))) {
                                            if (x to y !in list) {
                                                val index = list.indexOf(initialPositionX to initialPositionY)
                                                list.removeAt(index)

                                                offsetX = x; initialPositionX = offsetX
                                                offsetY = y; initialPositionY = offsetY
                                                list.add(index, initialPositionX to initialPositionY)

                                                turn = !white
                                            }
                                        } else {
                                            offsetX = initialPositionX
                                            offsetY = initialPositionY
                                        }
                                    }

                                    if (x to y in Logic().stepsForCheckers2(initialPositionX to initialPositionY, white, x, y, queen)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            activeList.clear()
                                            activeList.addAll(Logic().activateFreeze(turn))
                                            if (activeList.isEmpty()) turn = !white
                                            if (Logic().cell(coordinates) !in activeList) {
                                                activeList.clear()
                                                turn = !white
                                            }
                                        }
                                    } else {
                                        offsetX = initialPositionX
                                        offsetY = initialPositionY
                                    }


                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep1(initialPositionX to initialPositionY, white, 0.0, 0.0), 4))) {
                                    if (x to y in Logic().queenStep1(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            } else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep2(initialPositionX to initialPositionY, white, 0.0, 0.0), 3))) {
                                    if (x to y in Logic().queenStep2(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            } else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep3(initialPositionX to initialPositionY, white, 0.0, 0.0), 2))) {
                                    if (x to y in Logic().queenStep3(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            }  else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep4(initialPositionX to initialPositionY, white, 0.0, 0.0), 1))) {
                                    if (x to y in Logic().queenStep4(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)


                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            }  else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep5(initialPositionX to initialPositionY, white, 0.0, 0.0), 4))) {
                                    if (x to y in Logic().queenStep5(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)


                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            }  else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep6(initialPositionX to initialPositionY, white, 0.0, 0.0), 3))) {
                                    if (x to y in Logic().queenStep6(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)


                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            }  else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep7(initialPositionX to initialPositionY, white, 0.0, 0.0), 2))) {
                                    if (x to y in Logic().queenStep7(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            }  else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }

                                if (queen && (activeList.isEmpty() || Logic().checkActiveQueen(initialPositionX to initialPositionY, Logic().queenStep8(initialPositionX to initialPositionY, white, 0.0, 0.0), 1))) {
                                    if (x to y in Logic().queenStep8(initialPositionX to initialPositionY, white, x, y)) {
                                        if (x to y !in list) {
                                            val index = list.indexOf(initialPositionX to initialPositionY)
                                            list.removeAt(index)

                                            if (initialPositionX to initialPositionY in queens) {
                                                queens.remove(initialPositionX to initialPositionY)
                                                queens.add(x to y)
                                            }

                                            offsetX = x; initialPositionX = offsetX
                                            offsetY = y; initialPositionY = offsetY
                                            list.add(index, initialPositionX to initialPositionY)

                                            if (Logic().cell(coordinates) !in activeList && offsetX to offsetY !in activeList) {
                                                turn = !white
                                            } else {
                                                activeList.clear()
                                                activeList.addAll(Logic().activateFreeze(turn))
                                                if (activeList.isEmpty()) turn = !white
                                                if (Logic().cell(coordinates) !in activeList) {
                                                    activeList.clear()
                                                    turn = !white
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    offsetX = initialPositionX
                                    offsetY = initialPositionY
                                }
                            }


                            if (Logic().cell(coordinates) in activeList && turn == white) {
                                activeList.clear()
                                activeList.add(Logic().cell(coordinates))
                            } else {
                                activeList.addAll(Logic().activateFreeze(turn))
                            }


                            if (!queen) {
                                queen = optional(offsetY, white)
                                if (queen) {
                                    queens.add(offsetX to offsetY)
                                }
                            }

                            checkEndGame(endGame())
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .width(89.dp).height(89.dp).clip(CircleShape)
                    .background(color)
                    .clickable {}
            )
            if (queen) {
                Image(
                    painter = painterResource("/myCrown.png"),
                    contentDescription = "Localized description",
                )
            }
        }
    }
    return delete
}


fun fillingList() {
    if (!restart.value) {
        for (element in (Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)) {
            list.add(Logic().cell(element))
        }
    }
}


fun activeBackground(coordinates: String, activeList: List<Pair<Double, Double>>, queen: Boolean): Color {
    var color: Color = Color.Black
    if (Logic().cell(coordinates) in activeList) color = Color.Green
    if (queen) color = Color.Yellow
    if (queen && Logic().cell(coordinates) in activeList) color = Color.Blue
    return color
}


fun optional(offset: Double, white: Boolean): Boolean {
    if (white) {
        if (offset == 38.0) return true
    }
    if (!white) {
        if (offset == 808.0) return true
    }
    return false
}


@Composable
fun checkers() {
    if (restart.value) restart.value = false
    for (element in (Logic().coordinatesWhiteCheckers + Logic().coordinatesBlackCheckers)) {
        if (element in Logic().coordinatesWhiteCheckers) {
            counting(Color.White, element, true, Logic().cell(element) in delete)
        }
        if (element in Logic().coordinatesBlackCheckers) {
            counting(Color.DarkGray, element, false, Logic().cell(element) in delete)
        }
    }
}


val restart = mutableStateOf(false)
fun restartGame() {
    turn = false
    actives.clear()
    list.clear()
    delete.clear()
    queens.clear()
    restart.value = true
}

fun skipTurn() {
    turn = false
}

fun endGame(): String {
    var countBlack = 0
    var countWhite = 0
    var win = ""
    for (elem in list.subList(12, 24)) {
        if (elem == 0.0 to 0.0) countBlack++
    }
    for (elem in list.subList(0, 12)) {
        if (elem == 0.0 to 0.0) countWhite++
    }
    if (countBlack == 12) win = "Белые победили"
    if (countWhite == 12) win = "Черные победили"
    return win
}

val text = mutableStateOf("")
val openDialog = mutableStateOf(false)

fun checkEndGame(endGame: String) {
    if (endGame != "") {
        text.value = endGame
        openDialog.value = true
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun alertDialog() {
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            text = { Text(text = text.value) },
            buttons = {
                Button(
                    onClick = {
                        openDialog.value = false
                        restartGame()
                    }
                ) {
                    Text(
                        "Рестарт",
                        fontSize = 22.sp,
                    )
                }
            }
        )
    }
}

@Composable
fun menu() {
    var expanded by remember { mutableStateOf(false) }
    Column {
        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    Icons.Default.MoreVert, contentDescription = "Показать меню",
                    modifier = Modifier
                        .size(30.dp)
                        .offset((-8).dp, 0.dp)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .size(100.dp)
            ) {
                Text(
                    "Рестарт",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                expanded = false
                                restartGame()
                            }
                        )
                )
                Text(
                    "Пропуск хода",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                expanded = false
                                skipTurn()
                            }
                        )
                )
            }
        }
    }
}













