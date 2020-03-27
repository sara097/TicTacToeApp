package com.example.tictactoeapp.gamePlay

abstract class Player { //Abstract class of player.
    abstract val name: String
    abstract val symbol: TicTacToeSymbol
}

class SimplePlayer( //Simple player represent player controlled by user.
    override val name: String,
    override val symbol: TicTacToeSymbol
) : Player()