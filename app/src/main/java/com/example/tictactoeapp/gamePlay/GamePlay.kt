package com.example.tictactoeapp.gamePlay

import com.pwr.zad1.TicTacToe

abstract class GamePlay { //Abstract class from TicTacToe game

    abstract val ttt: TicTacToe //Class with game methods

    abstract fun playGame() //Methods that allows to play game. It is implemented by children classes.
    abstract fun startGame(): Pair<Player, Player> //Creates users for game

    //Takes move coordinates from user until move is correct.
    fun userPlayerActivity(activePlayer: Player) {
        var moveCords: Map<String, Int?>? = getProperCords(player = activePlayer)
        var movePerformed =
            ttt.makeMove(
                activePlayer.symbol,
                moveCords?.get("row"),
                moveCords?.get("column"),
                moveCords?.get("layer")
            ) //Puts symbol into game board.
        while (!movePerformed) {
            moveCords = getProperCords(activePlayer)
            movePerformed =
                ttt.makeMove(
                    activePlayer.symbol,
                    moveCords?.get("row"),
                    moveCords?.get("column"),
                    moveCords?.get("layer")
                ) //Puts symbol into game board.
        }
    }

    //Performs getting coordinates method untill they are proper.
    private fun getProperCords(player: Player): Map<String, Int?>? {
        var moveCords: Map<String, Int?>? = null
        while (moveCords == null) {
            moveCords = getCords(player)
        }
        return moveCords
    }

    //Gain coordinates in form rowCoords; columnCoords.
    private fun getCords(player: Player): Map<String, Int?>? {
        println("${player.name}, give move coordinates!")
        val cords = readLine() ?: return null
        return try {

                val cordList = cords.replace(" ", "").split(";").map { it.toInt() }
                if (cordList.size != 2 || cordList[0] !in 1..3 || cordList[1] !in 1..3) throw IllegalArgumentException()
                mapOf(
                    "row" to cordList[0] - 1,
                    "column" to cordList[1] - 1
                )
        } catch (e: NumberFormatException) { //If coordinates format is wrong.
            println("You must pick NUMBERS in proper format: rowCord; columnCord")
            null
        } catch (e: IllegalArgumentException) { //If coordinates are not 1, 2 or 3. (1,2,3 or 4 in 3D)
            println("You must pick natural numbers 1, 2 or 3.")
            null
        } catch (e: Exception) { //In case of unexpected user input.
            println("Do not mess with me. Do it right!")
            null
        }
    }


}