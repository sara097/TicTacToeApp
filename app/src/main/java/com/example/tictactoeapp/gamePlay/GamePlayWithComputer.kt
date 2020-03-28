package com.example.tictactoeapp.gamePlay

import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.tictactoeapp.R
import com.example.tictactoeapp.gamePlay.TicTacToe.ComputerPlayer

class GamePlayWithComputer(
    override var ttt: TicTacToe,
    private val buttons: Array<Array<ImageButton>>
) : GamePlay() {

    private fun getDrawable(symbol: TicTacToeSymbol?): Int {
        return when (symbol) {
            TicTacToeSymbol.X -> R.drawable.x
            TicTacToeSymbol.O -> R.drawable.o
            else -> R.drawable.empty
        }
    }

    //Method, that allows to play game until someone win or there is no EMPTY in table
    override fun playGame(
        map: Map<String, String>,
        label: TextView,
        cords: Pair<Int, Int>,
        btn: Button
    ): TicTacToeSymbol? {
        if (!ttt.gameOver) {
            val players = startGame(map) //Getting users
            val activePlayer = if (ttt.moveCounter % 2 == 1) players.first else players.second //take active player
            val otherPlayer = if (ttt.moveCounter % 2 == 0) players.first else players.second //take active player
            label.text = "Your move ${otherPlayer.name}"
            if (activePlayer is SimplePlayer) {
                //if active player is user
                val u = userPlayerActivity(activePlayer, cords.first, cords.second) //as for coordinates
                val t = ttt.checkScore(cords.first, cords.second, btn) //Checks if performed move ended game
                if (t != "") label.text = t
                if (!ttt.gameOver) {
                    val moveCords = (otherPlayer as ComputerPlayer).makeMove()  //calculate coords
                    buttons[moveCords.first][moveCords.second].setImageResource(getDrawable(otherPlayer.symbol))
                    buttons[moveCords.first][moveCords.second].isEnabled = false
                    ttt.makeMove(otherPlayer.symbol, moveCords.first, moveCords.second) //put it into table
                    val t2 =
                        ttt.checkScore(moveCords.first, moveCords.second, btn) //Checks if performed move ended game
                    if (t2 != "") label.text = t2
                }

                return u
            }
        }
        return null
    }


    //Specify which one player is computer, who starts and what is difficulty level
    override fun startGame(map: Map<String, String>): Pair<Player, Player> {
        val p = map["p"] ?: "Player"
        val symbol = when (map["symbol"]) {
            "O" -> TicTacToeSymbol.O
            else -> TicTacToeSymbol.X
        }
        val level = when (map["difMode"]) {
            "1" -> 1
            "2" -> 2
            else -> 3
        }
        return when (symbol) {
            TicTacToeSymbol.O -> ttt.ComputerPlayer(TicTacToeSymbol.X, level) to SimplePlayer(p, TicTacToeSymbol.O)
            else -> SimplePlayer(p, TicTacToeSymbol.X) to ttt.ComputerPlayer(TicTacToeSymbol.O, level)
        }
    }

    fun firstMove(map: Map<String, String>) {
        val players = startGame(map)
        val cp = players.first
        if (cp is ComputerPlayer) {
            val moveCords = cp.makeMove()  //calculate coords
            buttons[moveCords.first][moveCords.second].setImageResource(getDrawable(cp.symbol))
            buttons[moveCords.first][moveCords.second].isEnabled = false
            ttt.makeMove(cp.symbol, moveCords.first, moveCords.second) //put it into table
            ttt.checkScore(moveCords.first, moveCords.second) //Checks if performed move ended game
        }
    }


}