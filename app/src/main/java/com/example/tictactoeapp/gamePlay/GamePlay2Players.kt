package com.example.tictactoeapp.gamePlay

import com.pwr.zad1.TicTacToe

open class GamePlay2Players(
    override val ttt: TicTacToe
) : GamePlay(){

    //Method, that allows to play game until someone win or there is no EMPTY in table
    override fun playGame(){
        val players = startGame()
        var moveCounter = 1
        while (!ttt.gameOver) { //if there is EMPTY and no one win
            val activePlayer = if (moveCounter % 2 == 1) players.first else players.second //take active player
            userPlayerActivity(activePlayer) //ask for coordinates
            moveCounter++
        }
    }

    //Method that gain user names and symbols.
    override fun startGame(): Pair<Player, Player> {

    }

}