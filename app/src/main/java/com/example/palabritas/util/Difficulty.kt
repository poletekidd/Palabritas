package com.example.palabritas.util
import java.io.Serializable

open class Difficulty(val name: String?, val wordLength: Int) : Serializable {
    open fun description(): String {
        return "Nivel de dificultad: $name, con valor de longitud de palabra $wordLength."
    }
}

class Easy : Difficulty("Fácil", 3), Serializable {
    override fun description(): String {
        return "Nivel fácil: Ideal para principiantes. Dificultad de palabra $wordLength."
    }
}

class Mid(wordLength: Int) : Difficulty("Medio", wordLength), Serializable {
    override fun description(): String {
        return "Nivel medio: Ideal para usuarios con experiencia. Longitud de palabra $wordLength."
    }
}

class Hard : Difficulty("Difícil", 6), Serializable {
    override fun description(): String {
        return "Nivel difícil: Ideal para principiantes. Dificultad de palabra $wordLength."
    }
}
