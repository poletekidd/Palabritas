package com.example.palabritas

import Game
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.palabritas.util.Difficulty


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar la dificultad
        val difficulty: Difficulty = Difficulty.Easy()
        println(difficulty.description())

        // Leer las palabras desde el archivo de recursos (diccionario.txt)
        val archivo = assets.open("diccionario.txt")
        val lineas = archivo.bufferedReader().readLines()

        // Obtener una palabra aleatoria según la dificultad
        val game = Game(3, difficulty, this) // Usar contexto para Game
        val palabraAleatoria = Game.obtenerPalabraAleatoria(difficulty.wordLength, lineas)


        // Imprimir la palabra a adivinar
        val palabraAdivinar = game.getPalabraAdivinar()
        println("Palabra a adivinar: $palabraAdivinar")

        // Obtener referencia al botón
        val buttonStartGame: Button = findViewById(R.id.btEmpezarPartida)

        // Asignar el listener al botón
        buttonStartGame.setOnClickListener {
            // Preparar el Intent para pasar los datos a GameActivity
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("difficulty", difficulty)
            intent.putExtra("gameData", game)
            intent.putExtra("lineas", ArrayList(lineas)) // Pasar las líneas como ArrayList
            startActivity(intent)
        }
    }
}
