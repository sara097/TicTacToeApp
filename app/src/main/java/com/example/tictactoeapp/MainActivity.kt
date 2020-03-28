package com.example.tictactoeapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val players = findViewById<Button>(R.id.players)
        players.setOnClickListener {
            val intent = Intent(this, PlayersActivity::class.java)
            startActivity(intent)
        }
        val computer = findViewById<Button>(R.id.computer)
        computer.setOnClickListener {
            val intent = Intent(this, ModeActivity::class.java)
            startActivity(intent)
        }
    }

}
