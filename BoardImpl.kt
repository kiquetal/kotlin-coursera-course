package board

import board.Direction.*
import java.util.function.Consumer

fun createSquareBoard(width: Int): SquareBoard = MyMatrix(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = MyBoard<T>(width)



class MyMatrix(override val width: Int) : SquareBoard {

    val myCell=Array(width) { row ->
        Array(width) { col ->
            Cell(row + 1, col + 1)
        }
    }

    fun printElement() {
        this.myCell.forEach { arrayOfCells ->

            arrayOfCells.forEach { c->
                println(c)
            }
        }


    }


    fun Array<Array<Cell>>.toMap():MutableMap<Cell,Cell>  {
        val m= mutableMapOf<Cell,Cell>()
        this.forEach { row->
            row.forEach { col  ->
                m[col]=col

            }
        }
        return m;

    }
    private fun isValidPosition(i:Int, j:Int):Boolean {

        return when {
            i<1 -> false
            i>width->false
            j<1 ->false
            j>width->false
            else-> true

        }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {

        return if (!isValidPosition(i,j))
            null
        else{
            myCell[i-1][j-1]
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        require(isValidPosition(i, j)) { "IllegalArgumentException cell does not exist" }
        return myCell[i-1][j-1]

    }


    override fun getAllCells(): Collection<Cell> {
        val m= mutableListOf<Cell>()
        this.myCell.forEach { arrayOfCells ->

            arrayOfCells.forEach { cell->
                m.add(cell)
            }
        }
        return m
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {

        val m= mutableListOf<Cell>()
        jRange.forEach(Consumer { t ->
            if (this.isValidPosition(i,t))
                m.add(this.getCell(i,t))
        })

        return m

    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {

        val m= mutableListOf<Cell>()
        iRange.forEach(Consumer { c ->
            println("check valid ${c},${j}"+this.isValidPosition(c,j))
            if (this.isValidPosition(c,j)) {
                m.add(this.getCell(c, j))
            }
        })

        return m
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {

        return when (direction)
        {
            Direction.UP-> getCellOrNull(this.i-1,this.j)
            Direction.LEFT-> getCellOrNull(this.i,this.j-1)
            Direction.DOWN-> getCellOrNull(this.i+1,this.j)
            Direction.RIGHT-> getCellOrNull(this.i,this.j+1)
        }

    }


}
class MyBoard<T>(override val width: Int) :GameBoard<T>
{

    var myMatrix= mutableMapOf<Cell,T>()
    var m= arrayOfNulls<Any?>(width*width)
    private var idx=0
    override fun getCellOrNull(i: Int, j: Int): Cell? {

        return myMatrix.filterKeys { cell -> cell == Cell(i,j) }.keys.first()
    }

    override fun getCell(i: Int, j: Int): Cell {
        println("getCell"+i+"-"+j)
        println("if emtpy"+myMatrix.isEmpty())
        if (myMatrix.isEmpty()) {
         return   Cell(i,j)
        }
      return when{
               myMatrix.keys.filter { c-> c.i==i && c.j==j }.size>0 -> myMatrix.keys.filter { c-> c.i==i && c.j==j }.first()

           else -> Cell(i,j)


       }

    }

    override fun getAllCells(): Collection<Cell> {
        return myMatrix.keys
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val myList= arrayListOf<Cell>()
        jRange.forEach{ j ->
            if (myMatrix.filter { entry -> entry.key == Cell(i,j)  }.size>0)
                myList.add(Cell(i,j))
        }
        return myList
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {

        val myList= arrayListOf<Cell>()
        iRange.forEach{ l ->
            if (myMatrix.filter { entry -> entry.key == Cell(l,j)  }.size>0)
                myList.add(Cell(l,j))
        }
        return myList
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction)
        {
            Direction.UP-> getCellOrNull(this.i-1,this.j-1)
            Direction.LEFT-> getCellOrNull(this.i-i,this.j)
            Direction.DOWN-> getCellOrNull(this.i+1,this.j)
            Direction.RIGHT-> getCellOrNull(this.i,this.j+1)
        }
    }

    override fun get(cell: Cell): T? {

        return myMatrix.get(cell)

    }

    override fun set(cell: Cell, value: T?) {
        println("introducir"+cell + "val" + value)
        if (value != null) {
            myMatrix[cell] = value
            m[idx++]=value
        }
        println(myMatrix)
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        println(myMatrix)
        return myMatrix.filterValues { predicate(it) }.keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return myMatrix.filterValues { predicate(it) }.keys.first()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {

       return m.any { predicate(it as T?) }

    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return m.all { predicate(it as T?) }
    }

}




