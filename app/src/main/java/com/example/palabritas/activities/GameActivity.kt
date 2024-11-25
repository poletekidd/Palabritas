package com.example.palabritas.activities

import Game
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.palabritas.R
import com.example.palabritas.util.Difficulty

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)


        // Recuperar los datos pasados en el Intent
        var txtViewWordToGuess: TextView = findViewById(R.id.txtViewWordToGuess)
        val game = intent.getSerializableExtra("gameData") as? Game
        val difficulty = intent.getSerializableExtra("difficulty") as? Difficulty
        txtViewWordToGuess.text = game?.getPalabraAdivinar()

        // Obtener referencia al botón
        val btGuess: Button = findViewById(R.id.btGuess)
        val editTextGuess: EditText = findViewById(R.id.editTextGuess)

        // Asignar el listener al botón
        btGuess.setOnClickListener {

            println(game?.validarPalabraAdivinada(editTextGuess.toString()))
            if(game?.validarPalabraAdivinada(editTextGuess.toString()) == true) {

                if (difficulty != null) {
                    game.siguientePalabra(difficulty)
                }
            }

            if(game?.numeroIntentos == 0) {
                Toast.makeText(this, "Juego terminado. Se han agotado los intentos.", Toast.LENGTH_SHORT).show()

                // Finalizar la actividad
                finish()
            }
        }

    }
}