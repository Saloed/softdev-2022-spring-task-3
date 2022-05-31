package softdev.spring.task

class CellS(private var x: Int, private var y: Int, private val vector: Int) {

    //VECTOR: 1 - UP, 2- DOWN, 3 - LEFT, 4 - RIGHT

    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    fun getV(): Int {
        return vector
    }
}