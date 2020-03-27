package com.example.tictactoeapp.gamePlay

fun ILLEGAL(message : String = "Error occured!"){
    throw WrongMoveException(message)
}

class WrongMoveException(message: String) : Exception(message)