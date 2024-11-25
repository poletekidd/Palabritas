import android.content.Context
import com.example.palabritas.util.Difficulty
import java.io.Serializable

class Game(
    var numeroIntentos: Int,
    val difficulty: Difficulty,
    val context: Context // Añadimos el contexto como parámetro
) : Serializable {

    private var palabraAdivinar: String? = null
    private var lineas: List<String>? = null

    init {
        // Cargar las líneas del archivo solo cuando se cree el objeto Game
        lineas = obtenerLineasDeDiccionario(context)
    }

    // Método para obtener la palabra
    fun getPalabraAdivinar(): String? {
        return palabraAdivinar
    }

    // Método para validar si la palabra adivinada es correcta
    fun validarPalabraAdivinada(palabra: String): Boolean {
        var isCorrecto = false
        if (palabraAdivinar.equals(palabra, ignoreCase = true)) {
            isCorrecto = true
        } else {
            numeroIntentos -= 1
        }
        return isCorrecto
    }

    // Método para obtener una palabra aleatoria (sin el uso de Contexto)
    companion object {
        fun obtenerPalabraAleatoria(longitud: Int, lineas: List<String>): String? {
            // Filtrar las palabras por longitud
            val palabrasFiltradas = lineas.filter { it.length == longitud }

            // Seleccionar una palabra aleatoria si la lista no está vacía
            return if (palabrasFiltradas.isNotEmpty()) {
                palabrasFiltradas.random()
            } else {
                null // Si no hay palabras de esa longitud
            }
        }
    }

    // Método para obtener las líneas de diccionario
    private fun obtenerLineasDeDiccionario(context: Context): List<String> {
        val archivo = context.assets.open("diccionario.txt")
        return archivo.bufferedReader().readLines()
    }

    // Cambiar la palabra y restablecer los intentos
    fun siguientePalabra(difficulty: Difficulty) {
        palabraAdivinar = obtenerPalabraAleatoria(difficulty.wordLength, lineas ?: emptyList())
        numeroIntentos = 3
    }
}
