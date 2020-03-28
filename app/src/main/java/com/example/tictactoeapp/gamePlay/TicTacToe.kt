package com.example.tictactoeapp.gamePlay

import android.view.View
import android.widget.Button
import com.example.tictactoeapp.gamePlay.TicTacToeSymbol.*

open class TicTacToe {

    private lateinit var table: Array<Array<TicTacToeSymbol>> //Two dimensional table 3x3
    var moveCounter: Int = 1
    open var gameOver: Boolean = false //define if game is over

    init { //initializes class
        fillTableWithEmpty() //fills whole table with EMPTY symbol
        printTable() //prints table in console
    }

    inner class ComputerPlayer( //Player controlled by computer
        override val symbol: TicTacToeSymbol,
        private val level: Int //difficulty level
    ) : Player() {
        override val name: String
            get() = "Computer"

        //Defines algorithm od making moves by computer based on difficulty level.
        fun makeMove(): Pair<Int, Int> =
            when (level) {
                1 -> findThird(symbol)
                    ?: putAnywhere() //Easiest way. Computer tries to win, if cant puts symbol on first EMPTY
                2 -> findThird(symbol) ?: findThird(oppositeSymbol(symbol))
                ?: putAnywhere() //Computer tries to win, then tries to block you, if cant puts symbol on first EMPTY
                else -> findThird(symbol) ?: findThird(oppositeSymbol(symbol)) ?: tryToPutNext() ?: putAnywhere()
                //Computer tries to win, then tries to block you, then tries to have to symbols next to each other,
                // if cant puts symbol on first EMPTY
            }

        //method that finds opposite symbol.
        private fun oppositeSymbol(sym: TicTacToeSymbol) = when (sym) {
            X -> O
            O -> X
            else -> EMPTY
        }

        //Seeks if table contains winning situation possible in one move.
        private fun findThird(sym: TicTacToeSymbol): Pair<Int, Int>? {
            for (i in 0..2) {
                val row = table[i] //Takes row
                val col = table.map { it[i] }.toList() //Takes column
                //If row contains two occurrences of wanted symbol and contains EMPTY return coordinates of EMPTY
                if (row.count { it == sym } == 2 && row.contains(EMPTY)) return i to row.indexOf(EMPTY)
                //If column contains two occurrences of wanted symbol and contains EMPTY return coordinates of EMPTY
                if (col.count { it == sym } == 2 && col.contains(EMPTY)) return col.indexOf(EMPTY) to i
            }
            //3x3 table has two diagonal vectors
            //First diagonal.
            val diag1 = listOf(table[0][0], table[1][1], table[2][2])
            //If diagonal contains two occurrences of wanted symbol and contains EMPTY return coordinates of EMPTY
            if (diag1.count { it == sym } == 2 && diag1.contains(EMPTY)) return diag1.indexOf(EMPTY) to diag1.indexOf(
                EMPTY
            )
            //Second diagonal.
            val diag2 = listOf(table[0][2], table[1][1], table[2][0])
            //If diagonal contains two occurrences of wanted symbol and contains EMPTY return coordinates of EMPTY
            if (diag2.count { it == sym } == 2 && diag2.contains(EMPTY)) return diag2.indexOf(EMPTY) to 2 - diag2.indexOf(
                EMPTY
            )
            return null //if there is no such situation, returns null
        }

        //Tries to put symbol next to same symbol.
        private fun tryToPutNext(): Pair<Int, Int>? {
            for (i in 0..2) {
                val row = table[i] //Takes row.
                val col = table.map { it[i] }.toList() //Takes column

                //Seeks for symbol in row or column and two EMPTY. If finds, returns coordinates of first EMPTY
                if (row.count { it == symbol } == 1 && row.count { it == EMPTY } == 2) return i to row.indexOfFirst { it == EMPTY }
                if (col.count { it == symbol } == 1 && col.count { it == EMPTY } == 2) return col.indexOfFirst { it == EMPTY } to i
            }

            //3x3 table has two diagonal vectors
            //Seeks for symbol in diagonal and two EMPTY. If finds, returns coordinates of  first EMPTY
            //First diagonal.
            val diag1 = listOf(table[0][0], table[1][1], table[2][2])
            if (diag1.count { it == symbol } == 1 && diag1.count { it == EMPTY } == 2) return diag1.indexOfFirst { it == EMPTY } to diag1.indexOfFirst { it == EMPTY }
            //Second diagonal.
            //Seeks for symbol in diagonal and two EMPTY. If finds, returns coordinates of first EMPTY
            val diag2 = listOf(table[0][2], table[1][1], table[2][0])
            if (diag2.count { it == symbol } == 1 && diag2.count { it == EMPTY } == 2) return diag2.indexOfFirst { it == EMPTY } to 2 - diag2.indexOfFirst { it == EMPTY }

            return null //if doesnt find it, returns null.
        }

        //Puts symbol in random EMPTY.
        private fun putAnywhere(): Pair<Int, Int> {
            val randRow = listOf(0, 1, 2).shuffled() //randomize row indices
            val randCol = listOf(0, 1, 2).shuffled()//randomize row indices
            for (i in randRow)
                for (j in randCol)
                    if (table[i][j] == EMPTY) return i to j //puts symbol on first EMPTY
            return 1 to 1
        }

    }

    //initializes table with EMPTY symbol.
    open fun fillTableWithEmpty() {
        table = arrayOf()
        for (i in 0..2) {
            var array = arrayOf<TicTacToeSymbol>()
            for (j in 0..2)
                array += EMPTY
            table += array
        }
    }

    //Prints table.
    open fun printTable() {
        listOf("   ", " 1 ", " 2 ", " 3 ").forEach { print(it) }
        println()
        table.forEach { row ->
            print(" ${table.indexOf(row) + 1} ")
            row.forEach { print("|${it.symbol}|") }
            println()
        }
    }

    //Puts symbol in desire location
    open fun makeMove(
        symbol: TicTacToeSymbol,
        rowCoord: Int,
        columnCord: Int
    ): TicTacToeSymbol? {
        var movePerformed: TicTacToeSymbol? = null //Flag indicating whether the move was made.

        when {
            symbol == EMPTY -> ILLEGAL("You did not make a move.") // checks if symbol is correct.
            table[rowCoord][columnCord] == EMPTY -> { //if desired location is EMPTY
                table[rowCoord][columnCord] = symbol //puts symbol in this location
                movePerformed = symbol //sets move flag as true
            }
        }
        printTable() //prints table
        if (movePerformed != null) moveCounter++
        println("******* $moveCounter")
        return movePerformed
    }

    //Checks if game has ended
    open fun checkScore(rowCoord: Int, columnCord: Int, btn: Button? = null): String {
        val possibilities = listOf( //list with possible places of win situations
            table.map { it[columnCord] }, //column with last put symbol
            table[rowCoord].toList(), //row with last put symbol
            listOf(table[0][0], table[1][1], table[2][2]), //diagonal 1
            listOf(table[0][2], table[1][1], table[2][0]) //diagonal 2
        )
        var text = ""
        for (t in values().filter { it != EMPTY }) //checking for X and O
            if (possibilities.map { it.count { elem -> elem == t } }.contains(3)) { //if any of possible places contains 3 of given symbol
                text = "$t won!"
                gameOver = true //End game
                break
            } else
                if (!table.flatMap { it.asList() }.contains(EMPTY)) { //If there is no EMPTY in table
                    text = "Tie."
                    gameOver = true //end game
                    break
                }
        if (btn != null)
            if (gameOver) btn.visibility = View.VISIBLE
        return text
    }
}