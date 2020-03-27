package com.example.tictactoeapp.gamePlay

import com.pwr.zad1.TicTacToe

class GamePlayWithComputer(
    override val ttt: TicTacToe
) : GamePlay() {

    //Method, that allows to play game until someone win or there is no EMPTY in table
    override fun playGame() {
        val players = startGame() //Getting users
        var moveCounter = 1
        while (!ttt.gameOver) { //if there is EMPTY and no one win
            val activePlayer = if (moveCounter % 2 == 1) players.first else players.second //take active player
            if (activePlayer is SimplePlayer) //if active player is user
                userPlayerActivity(activePlayer) //as for coordinates
            else if (activePlayer is TicTacToe.ComputerPlayer) { //if active player is computer
                val moveCords = activePlayer.makeMove() //calculate coords
                ttt.makeMove(activePlayer.symbol, moveCords.first, moveCords.second) //put it into table
            }
            moveCounter++
        }
    }

    //Define who starts.
    private fun whoStarts(): String {
        println("Which one player do you want to be? Player 1 (X) - type 1, Player 2 (O) - type 2. Player 1 begins game.")
        var mode = readLine()
        while (mode != "2" && mode != "1") {
            println("Give correct number. \n Which one player do you want to be? Player 1 (X) - type 1, Player 2 (O) - type 2. Player 1 begins game.")
            mode = readLine()
        }
        return mode
    }

    //Define difficulty level of game.
    private fun difficultyLevel(): Int {
        println("Choose level: 1 - easy, 2 - medium, 3 - hard.")
        var level = readLine()
        while (level != "3" && level != "2" && level != "1") {
            println("Give correct number. \n Choose level: 1 - easy, 2 - medium, 3 - hard.")
            level = readLine()
        }
        return when (level) {
            "1" -> 1
            "2" -> 2
            else -> 3
        }
    }

    //Specify which one player is computer, who starts and what is difficulty level
    override fun startGame(): Pair<Player, Player> {
        val mode = whoStarts() //Define who starts.
        val level = difficultyLevel() //Define difficulty level of game.
        //Asks user for name.
        val p1: Player
        val p2: Player
        when (mode) {
            "1" -> {
                println("You have chosen Player 1 (X), pick your name!")
                p1 = SimplePlayer(readLine() ?: "player1", TicTacToeSymbol.X)
                p2 = ttt.ComputerPlayer(TicTacToeSymbol.O, level)
            }
            else -> {
                println("You have chosen Player 2 (O), pick your name!")
                p1 = ttt.ComputerPlayer(TicTacToeSymbol.X, level)
                p2 = SimplePlayer(readLine() ?: "player2", TicTacToeSymbol.O)
            }
        }
        println("Player 1 - ${p1.name} plays with ${p1.symbol.symbol}, Player 2 - ${p2.name} plays with ${p2.symbol.symbol}")
        println("Game begins! Player1 ${p1.name} starts. Choose cord in format: row cord; column cord")
        return p1 to p2 //returns pair of users
    }


}