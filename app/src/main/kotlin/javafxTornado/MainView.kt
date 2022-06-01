package javafxTornado

import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import softdev.spring.task.CellS
import softdev.spring.task.Snake
import kotlin.concurrent.timer
import softdev.spring.task.*
import softdev.spring.task.Vector
import tornadofx.*
import java.util.*

var twoPlayersCondition = false

var eatToLiveCondition = false

val snakeImage = Image("Snake.png")

val foodImage = Image("AppleSnake.png")

val gameOver = Image("gameover.png")

val bonusImage = Image("BonusSnake.png")

val wasdSnakeImage = Image("naruto.png")

object MoveSnakeEvent : FXEvent()

object MoveSnakeEvent2 : FXEvent()


class MainView : View() {
    override val root = BorderPane()

    private lateinit var canvas: Canvas

    private var a = timer(period = speed1, daemon = true) {
        fire(MoveSnakeEvent)
    }

    private lateinit var b: Timer

    private fun restartA() {
        a.cancel()
        a = timer(period = speed1, daemon = true) {
            fire(MoveSnakeEvent)
        }
    }

    private fun restartB() {
        b.cancel()
        b = timer(period = speed2, daemon = true) {
            fire(MoveSnakeEvent2)
        }
    }

    private fun snakePaint(snake: Snake, image: Image, list: MutableList<CellS>) {
        for (i in 0 until snake.length) {
            canvas.graphicsContext2D.drawImage(
                image,
                list[i].getX().toDouble(),
                list[i].getY().toDouble()
            )
        }
    }

    private fun mainPaint(snake: Snake, snake2: Snake) {
        if (alive) {
            if (food != null) canvas.graphicsContext2D.drawImage(
                foodImage,
                food!!.getX().toDouble(),
                food!!.getY().toDouble()
            )
            if (bonus != null) canvas.graphicsContext2D.drawImage(
                bonusImage,
                bonus!!.getX().toDouble(),
                bonus!!.getY().toDouble()
            )
            snakePaint(snake, snakeImage, cellsSnake)
            if (twoPlayersCondition) snakePaint(snake2, wasdSnakeImage, cellsSnake2)
        }
    }

    private fun dead() {
        a.cancel()
        a.purge()
        if (twoPlayersCondition) {
            b.cancel()
            b.purge()
        }
        canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
        canvas.graphicsContext2D.drawImage(
            gameOver,
            (fieldSize / 2 - 16).toDouble(),
            (fieldSize / 2 - 16).toDouble()
        )
    }

    init {

        if (twoPlayersCondition) {
            b = timer(period = speed2, daemon = true) {
                fire(MoveSnakeEvent2)
            }
            for (i in 0 until wasdSnake.length) {
                cellsSnake2 += CellS(48 - i * sizeOfOne, 96, Vector.RIGHT)
            }
        }

        for (i in 0 until firstSnake.length) {
            cellsSnake += CellS(48 - i * sizeOfOne, 48, Vector.RIGHT)
        }

        for (i in 0 until 20) {
            for (k in 0 until 20) freeCellsMap[CellFree(i * 16, k * 16)] = true
        }

        food = createFood()

        bonus = createBonus(firstSnake, wasdSnake)

        if (eatToLiveCondition) eatToLive(firstSnake, cellsSnake)

        with(root) {
            center {
                title = "SNAKE GAME"
                style = "-fx-background-color: black"
                canvas = canvas {
                    isFocusTraversable = true
                    width = fieldSize.toDouble()
                    height = fieldSize.toDouble()
                    setOnKeyPressed {
                        val event = it.code
                        if (event == KeyCode.UP && !firstSnake.down) {
                            with(firstSnake) {
                                up = true
                                left = false
                                right = false
                            }
                        }
                        if (event == KeyCode.DOWN && !firstSnake.up) {
                            with(firstSnake) {
                                down = true
                                left = false
                                right = false
                            }
                        }
                        if (event == KeyCode.RIGHT && !firstSnake.left) {
                            with(firstSnake) {
                                right = true
                                up = false
                                down = false
                            }
                        }
                        if (event == KeyCode.LEFT && !firstSnake.right) {
                            with(firstSnake) {
                                left = true
                                up = false
                                down = false
                            }
                        }

                        if (twoPlayersCondition) {
                            if (event == KeyCode.W && !wasdSnake.down) {
                                with(wasdSnake) {
                                    up = true
                                    left = false
                                    right = false
                                }
                            }
                            if (event == KeyCode.S && !wasdSnake.up) {
                                with(wasdSnake) {
                                    down = true
                                    left = false
                                    right = false
                                }
                            }
                            if (event == KeyCode.D && !wasdSnake.left) {
                                with(wasdSnake) {
                                    right = true
                                    up = false
                                    down = false
                                }
                            }
                            if (event == KeyCode.A && !wasdSnake.right) {
                                with(wasdSnake) {
                                    left = true
                                    up = false
                                    down = false
                                }
                            }

                        }
                    }
                }
            }
        }

        subscribe<MoveSnakeEvent> {
            val speed = speed1
            checks(firstSnake, cellsSnake, wasdSnake)
            if (bonus != null) {
                checkBonus(firstSnake, cellsSnake, wasdSnake)
                if (speed != speed1) restartA()
            }
            if (alive) {
                canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
                mainPaint(firstSnake, wasdSnake)
            } else dead()
        }

        subscribe<MoveSnakeEvent2> {
            val speed = speed2
            checks(wasdSnake, cellsSnake2, firstSnake)
            if (bonus != null) {
                if (speed != speed2) restartB()
            }
            if (alive) {
                canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
                mainPaint(firstSnake, wasdSnake)
            } else dead()
        }
    }
}