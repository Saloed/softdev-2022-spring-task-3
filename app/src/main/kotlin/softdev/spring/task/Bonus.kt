package softdev.spring.task


sealed class Bonus(private val x: Int, private  val y: Int) {
    fun getX(): Int {
        return x
    }

    fun getY(): Int {
        return y
    }

    class IncreaseSize(x: Int, y: Int): Bonus(x, y) {
        //0
    }

    class DecreaseSize(x: Int, y: Int): Bonus(x, y){
        //1
    }

    class IncreaseSpeed(x: Int, y: Int): Bonus(x, y){
        //2
    }

    class DecreaseSpeed(x: Int, y: Int): Bonus(x, y){
        //3
    }


}
