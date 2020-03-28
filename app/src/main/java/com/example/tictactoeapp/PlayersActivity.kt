package com.example.tictactoeapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText

class PlayersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_players)

        val start = findViewById<Button>(R.id.startGame)
        val p1 = findViewById<EditText>(R.id.player1Name)
        val p2 = findViewById<EditText>(R.id.player2Name)

        start.setOnClickListener {
            val name1 = p1.text.toString()
            val name2 = p2.text.toString()
            val intent = Intent(this, GamePlayActivity::class.java).apply {
                putExtra("mode", 0)
                putExtra("p1", if (name1 == "") "Player1" else name1)
                putExtra("p2", if (name2 == "") "Player2" else name2)
            }
            startActivity(intent)
        }
    }

}