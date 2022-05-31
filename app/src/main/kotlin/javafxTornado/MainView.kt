package javafxTornado

import javafx.scene.canvas.Canvas
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.layout.BorderPane
import softdev.spring.task.CellS
import softdev.spring.task.Snake
import kotlin.concurrent.timer
import softdev.spring.task.*
import tornadofx.*
import java.util.*

var twoPlayersCondition = true

var eatToLiveCondition = false

val snakeImage = Image("Snake.png")

val foodImage = Image("AppleSnake.png")

val gameOver = Image("gameover.png")

val bonusImage = Image("BonusSnake.png")

val wasdSnakeImage = Image("naruto.png")

object DeadEvent : FXEvent()

object MoveSnakeEvent : FXEvent()

object MoveSnakeEvent2 : FXEvent()


class MainView : View() {
    override val root = BorderPane()

    private lateinit var canvas: Canvas

    private var a = timer(period = speed1, daemon = true) {
        fire(MoveSnakeEvent)
    }

    private lateinit var b: Timer

    private fun checks(snake: Snake, cells: MutableList<CellS>, snake2: Snake) {
        snake.move(cells)
        alive = snake.checkBump(cells)
        if (!alive) fire(DeadEvent)
        if (food != null) snake.checkEaten(food!!, cells)
        if (snake.checkEaten(food!!, cells)) food = createFood()
        if (bonus != null) checkBonus(snake, cells, snake2)
        if (!alive) fire(DeadEvent)
        if (twoPlayersCondition && alive) alive = snake.checkEachBump(firstSnake, wasdSnake, cellsSnake, cellsSnake2)
    }

    private fun checkBonus(snake: Snake, list: MutableList<CellS>, snake2: Snake) {
        if (list[0].getX() == bonus!!.getX() && list[0].getY() == bonus!!.getY()) {
            if (bonus is Bonus.DecreaseSpeed) {
                if (snake == firstSnake) {
                    speed1 *= 2
                    a.cancel()
                    a = timer(period = speed1, daemon = true) {
                        fire(MoveSnakeEvent)
                    }
                } else if (snake == wasdSnake && twoPlayersCondition) {
                    speed2 *= 2
                    b.cancel()
                    b = timer(period = speed2, daemon = true) {
                        fire(MoveSnakeEvent2)
                    }
                }
            }
            if (bonus is Bonus.DecreaseSize) {
                snake.length -= 3
                if (snake.length > 0) {
                    var i = 3
                    while (i > 0) {
                        list.removeLast()
                        i--
                    }
                } else {
                    alive = false
                }
            }
            if (bonus is Bonus.IncreaseSpeed) {
                if (snake == firstSnake) {
                    speed1 /= 2
                    a.cancel()
                    a = timer(period = speed1, daemon = true) {
                        fire(MoveSnakeEvent)
                    }
                } else if (snake == wasdSnake && twoPlayersCondition) {
                    speed2 /= 2
                    b.cancel()
                    b = timer(period = speed2, daemon = true) {
                        fire(MoveSnakeEvent2)
                    }
                }
            }
            if (bonus is Bonus.IncreaseSize) snake.changeSize(3, list)

            bonus = createBonus(snake, snake2)
        }
    }

    private fun eatToLive(snake: Snake, mutableList: MutableList<CellS>) {
        timer(period = 7000, daemon = true, initialDelay = 7000) {
            snake.length -= 1
            if (snake.length > 0) mutableList.removeLast() else {
                fire(DeadEvent)
            }
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

    init {
        if (twoPlayersCondition) {
            b = timer(period = speed2, daemon = true) {
                fire(MoveSnakeEvent2)
            }
            for (i in 0 until wasdSnake.length) {
                cellsSnake2 += CellS(48 - i * sizeOfOne, 96, 4)
            }
        }

        for (i in 0 until firstSnake.length) {
            cellsSnake += CellS(48 - i * sizeOfOne, 48, 4)
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

        fun dead() {
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

        subscribe<MoveSnakeEvent> {
            checks(firstSnake, cellsSnake, wasdSnake)
            if (alive) {
                canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
                mainPaint(firstSnake, wasdSnake)
            } else fire(DeadEvent)
        }

        subscribe<MoveSnakeEvent2> {
            checks(wasdSnake, cellsSnake2, wasdSnake)
            if (alive) {
                canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
                mainPaint(firstSnake, wasdSnake)
            } else fire(DeadEvent)
        }

        subscribe<DeadEvent> {
            dead()
        }
    }
}