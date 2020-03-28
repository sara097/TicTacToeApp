package com.example.tictactoeapp.gamePlay

import android.widget.Button
import android.widget.TextView

abstract class GamePlay { //Abstract class for TicTacToe game

    abstract var ttt: TicTacToe //Class with game methods

    abstract fun playGame(
        map: Map<String, String>,
        label: TextView,
        cords: Pair<Int, Int>,
        btn: Button
    ): TicTacToeSymbol?

    //Methods that allows to play game. It is implemented by children classes.
    abstract fun startGame(map: Map<String, String>): Pair<Player, Player> //Creates users for game

    //Takes move coordinates from user until move is correct.
    fun userPlayerActivity(activePlayer: Player, row: Int, column: Int): TicTacToeSymbol? = ttt.makeMove(
        activePlayer.symbol,
        row,
        column
    ) //Puts symbol into game board.


}