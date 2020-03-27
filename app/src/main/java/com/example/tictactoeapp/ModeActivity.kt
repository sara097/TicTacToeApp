package com.example.tictactoeapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.RadioButton



class ModeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mode)
        val start = findViewById<Button>(R.id.startGame)
        val p1 = findViewById<EditText>(R.id.player1Name)

        start.setOnClickListener {
            val intent = Intent(this, GamePlayActivity::class.java).apply {
                putExtra("mode", 1)
                putExtra("p1", p1.text)
                putExtra("symbol", getSelectedRadioButtonTxt(R.id.symbolRadio))
                putExtra("difMode", getSelectedRadioButtonTxt(R.id.diffRadio))
            }
            startActivity(intent)
        }
    }

    private fun getSelectedRadioButtonTxt(id : Int): CharSequence? {
        val group = findViewById<RadioGroup>(id)
        val selected = findViewById<RadioButton>(group.checkedRadioButtonId)
        return selected.text
    }


}