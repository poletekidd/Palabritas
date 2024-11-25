package com.example.palabritas.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.jsoup.Jsoup

class WordViewModel : ViewModel() {

    private val client = OkHttpClient()

    fun checkWordInWiktionary(word: String) = liveData<String>(Dispatchers.IO) {
        val url = "https://es.wiktionary.org/w/api.php"
        val urlWithParameters = "$url?action=parse&page=$word&prop=text&format=json"
        val request = Request.Builder()
            .url(urlWithParameters)
            .build()

        try {
            // Realizamos la solicitud HTTP
            val response = client.newCall(request).execute()

            // Si la respuesta no fue exitosa, emitimos un error
            if (!response.isSuccessful) {
                emit("Error en la solicitud: ${response.code}")
                return@liveData
            }

            val responseBody = response.body?.string()
            val jsonObject = JSONObject(responseBody ?: "")

            val parse = jsonObject.optJSONObject("parse")
            val text = parse?.optJSONObject("text")
            val htmlContent = text?.optString("*")

            // Verificamos si htmlContent es nulo o vacío
            if (htmlContent.isNullOrEmpty()) {
                emit("La palabra no existe en Wikcionario o no se pudo encontrar.")
                return@liveData
            }

            // Parseamos el contenido HTML
            val doc = Jsoup.parse(htmlContent)
            val definitions = doc.select("dl dd") // Selecciona las definiciones directamente

            // Emitimos la primera definición encontrada, o un mensaje de error si no hay ninguna
            val result = if (definitions.isNotEmpty()) {
                definitions.first()?.text() ?: "No se encontró una definición específica."
            } else {
                "No se encontraron definiciones."
            }

            emit(result)

        } catch (e: Exception) {
            // En caso de error, emitimos el mensaje de error
            emit("Error al realizar la solicitud: ${e.message}")
        }
    }
}
