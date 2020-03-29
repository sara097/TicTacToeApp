package com.example.tictactoeapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup


class ModeActivity : AppCompatActivity() {

    //Computer mode activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        val start = findViewById<Button>(R.id.startGame)
        val p1 = findViewById<EditText>(R.id.playerName) //Player name
        //Start button. Gets information about who starts, name of player and difficulty level
        start.setOnClickListener {
            val name = p1.text.toString()
            val intent = Intent(this, GamePlayActivity::class.java).apply {
                putExtra("mode", 1)
                putExtra("p1", if (name == "") "Player" else name)
                putExtra("symbol", getSelectedRadioButtonTxt(R.id.symbolRadio))
                putExtra("difMode", getSelectedRadioButtonTxt(R.id.diffRadio))
            }
            startActivity(intent)
        }
    }

    //Gets text from radio buttons group
    private fun getSelectedRadioButtonTxt(id: Int): CharSequence? {
        val group = findViewById<RadioGroup>(id)
        val selected = findViewById<RadioButton>(group.checkedRadioButtonId)
        return selected.text
    }


}