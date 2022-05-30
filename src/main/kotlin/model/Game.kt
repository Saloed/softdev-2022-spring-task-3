package model

interface Game {
    operator fun get(i: Int, j: Int): Int?

    fun initialize()

    fun hasLost(): Boolean

    fun hasWon(): Boolean

    fun canMove(direction: Direction): Boolean

    fun processMove(direction: Direction)
}