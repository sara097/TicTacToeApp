package com.example.tictactoeapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.tictactoeapp.gamePlay.GamePlay
import com.example.tictactoeapp.gamePlay.GamePlay2Players
import com.example.tictactoeapp.gamePlay.GamePlayWithComputer
import com.pwr.zad1.TicTacToe

class GamePlayActivity : AppCompatActivity() {

    lateinit var extras: Map<String, String>
    val mode by lazy { intent.getIntExtra("mode", 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)
        extras = getExtraFromIntent(mode)
        val gamePlay = when(mode){
            1 -> GamePlayWithComputer(TicTacToe())
            else -> GamePlay2Players(TicTacToe())
        }
    }



    private fun getExtraFromIntent(mode: Int): Map<String, String> {
        return when (mode) {
            1 -> mapOf(
                "p1" to intent.getStringExtra("p1"),
                "symbol" to intent.getStringExtra("symbol"),
                "difMode" to intent.getStringExtra("difMode")
            )
            else -> mapOf(
                "p1" to intent.getStringExtra("p1"),
                "p2" to intent.getStringExtra("p2")
            )
        }
    }

}