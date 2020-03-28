package com.example.tictactoeapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.tictactoeapp.gamePlay.GamePlay2Players
import com.example.tictactoeapp.gamePlay.GamePlayWithComputer
import com.example.tictactoeapp.gamePlay.TicTacToe
import com.example.tictactoeapp.gamePlay.TicTacToeSymbol
import com.example.tictactoeapp.gamePlay.TicTacToeSymbol.O
import com.example.tictactoeapp.gamePlay.TicTacToeSymbol.X

class GamePlayActivity : AppCompatActivity() {

    private lateinit var extras: Map<String, String>
    private val mode by lazy { intent.getIntExtra("mode", 0) }
    private val gamePlay by lazy {
        when (mode) {
            1 -> GamePlayWithComputer(TicTacToe(), getButtons())
            else -> GamePlay2Players(TicTacToe())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_play)
        extras = getExtraFromIntent(mode)
        val label = findViewById<TextView>(R.id.playerLabel)
        label.text = "Your move ${if (mode == 0) extras["p1"] else extras["p"]}"

        val againButton = findViewById<Button>(R.id.playAgain)
        getCords(againButton)
        againButton.visibility = View.INVISIBLE

        againButton.setOnClickListener {
            againButton.visibility = View.INVISIBLE
            label.text = "Your move ${if (mode == 0) extras["p1"] else extras["p"]}"
            gamePlay.ttt = TicTacToe()
            initButtons()
            getCords(againButton)
            if (gamePlay is GamePlayWithComputer) (gamePlay as GamePlayWithComputer).firstMove(extras)
        }
        if (gamePlay is GamePlayWithComputer) (gamePlay as GamePlayWithComputer).firstMove(extras)
    }


    private fun getCords(btn: Button) {
        val ids0 = listOf(R.id.p_0_0, R.id.p_0_1, R.id.p_0_2)
        val ids1 = listOf(R.id.p_1_0, R.id.p_1_1, R.id.p_1_2)
        val ids2 = listOf(R.id.p_2_0, R.id.p_2_1, R.id.p_2_2)
        ids0.forEachIndexed { idx, id -> setListener(id, 0 to idx, btn) }
        ids1.forEachIndexed { idx, id -> setListener(id, 1 to idx, btn) }
        ids2.forEachIndexed { idx, id -> setListener(id, 2 to idx, btn) }
    }

    fun initButtons() {
        getButtons().flatten().forEach {
            it.isEnabled = true
            it.setImageResource(R.drawable.empty)
        }
    }

    private fun setListener(id: Int, cords: Pair<Int, Int>, btn: Button) {
        findViewById<ImageButton>(id).setOnClickListener {
            val s = gamePlay.playGame(extras, findViewById(R.id.playerLabel), cords, btn)
            (it as ImageButton).setImageResource(getDrawable(s))
            it.isEnabled = false
        }
    }

    private fun getDrawable(symbol: TicTacToeSymbol?): Int {
        return when (symbol) {
            X -> R.drawable.x
            O -> R.drawable.o
            else -> R.drawable.empty
        }
    }

    private fun getView(id: Int): ImageButton = findViewById(id)

    private fun getButtons(): Array<Array<ImageButton>> = arrayOf(
        arrayOf(getView(R.id.p_0_0), getView(R.id.p_0_1), getView(R.id.p_0_2)),
        arrayOf(getView(R.id.p_1_0), getView(R.id.p_1_1), getView(R.id.p_1_2)),
        arrayOf(getView(R.id.p_2_0), getView(R.id.p_2_1), getView(R.id.p_2_2))
    )


    private fun getExtraFromIntent(mode: Int): Map<String, String> {
        return when (mode) {
            1 -> mapOf(
                "p" to intent.getStringExtra("p1"),
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