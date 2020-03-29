package com.example.tictactoeapp.gamePlay

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.TextView

open class GamePlay2Players(
    override var ttt: TicTacToe
) : GamePlay() {

    //Method, that allows to play game until someone win or there is no EMPTY in table
    @SuppressLint("SetTextI18n")
    override fun playGame(
        map: Map<String, String>,
        label: TextView,
        cords: Pair<Int, Int>,
        btn: Button
    ): TicTacToeSymbol? {
        if (!ttt.gameOver) {
            val players = startGame(map)
            val activePlayer = if (ttt.moveCounter % 2 == 1) players.first else players.second //take active player
            val otherPlayer = if (ttt.moveCounter % 2 == 0) players.first else players.second //take active player
            label.text = "Your move ${otherPlayer.name}"
            val u = userPlayerActivity(activePlayer, cords.first, cords.second) //ask for coordinates
            val t = ttt.checkScore(cords.first, cords.second, btn) //Checks if performed move ended game
            if (t != "") label.text = t
            return u
        }
        return null
    }

    //Method that gain user names and symbols.
    override fun startGame(map: Map<String, String>): Pair<Player, Player> {
        return SimplePlayer(map["p1"] ?: "Player1", TicTacToeSymbol.X) to
                SimplePlayer(map["p2"] ?: "Player2", TicTacToeSymbol.O)
    }

}