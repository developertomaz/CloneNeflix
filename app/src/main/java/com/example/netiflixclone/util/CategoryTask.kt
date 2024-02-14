package com.example.netiflixclone.util

import android.os.Looper
import android.util.Log
import com.example.netiflixclone.model.Category
import com.example.netiflixclone.model.Movie
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors
import java.util.logging.Handler

class CategoryTask(private val callback:Callback) {

    private val handler = android.os.Handler(Looper.getMainLooper())

    interface Callback {
        fun OnPressExcute()
        fun onResult(categories:List<Category>)
        fun onFailure(message:String)
    }



    fun execute(url:String){
        callback.OnPressExcute()


        // nesse momento, estamos utilizando a UI-thread (1)
        val executor = Executors.newSingleThreadExecutor()

        executor.execute {
            var urlConnection: HttpURLConnection? = null
            var buffer:BufferedInputStream?=null
            var stream:InputStream?=null

            try {
            //nesse momento, estamos utilizando a NOVA-thread [ processo paralelo](2)
         val requestUrl = URL(url) //abrir uma URL

          urlConnection=   requestUrl.openConnection() as HttpURLConnection //abrir conexão
          urlConnection.readTimeout = 2000 //tempo de leitura(2s)
          urlConnection.connectTimeout = 2000 //tempo de conexão(2s)

         val statusCode:Int = urlConnection.responseCode
           if (statusCode > 400) {
                 throw IOException("Erro na comunicação com o servidor!")
           }

                //forma simples e rápida
                stream =urlConnection.inputStream
            val jsonAstring= stream.bufferedReader().use { it.readText() }//byte ->String

                 val categories =toCategories(jsonAstring)

               handler.post {
                   callback.onResult(categories)
               }


        } catch(e: IOException) {
        val message = e.message?:"erro desconhecido"
            Log.e("test",message,e)

                handler.post{
                    callback.onFailure(message)
                }


        } finally {
               urlConnection?.disconnect()
                stream?.close()
                buffer?.close()
        }

        }

    }
    private fun toCategories(jsonAsString:String): List<Category>{
       val categories = mutableListOf<Category>()

        val jsonRoot =JSONObject(jsonAsString)
        val jsonCategories=  jsonRoot.getJSONArray("category")
         for (i in 0 until jsonCategories.length()) {
        val jsonCategory=     jsonCategories.getJSONObject(i)
        val title =jsonCategory.getString("title")
        val jsonMovies =jsonCategory.getJSONArray("movie")

             val movies = mutableListOf<Movie>()
             for (j in 0 until jsonMovies.length()) {
               val jsonMovie=  jsonMovies.getJSONObject(j)
                 val id = jsonMovie.getInt("id")
                 val coverUrl =jsonMovie.getString("cover_url")

             movies.add(Movie(id,coverUrl))
             }

             categories.add(Category(title,movies))
         }

        return categories
    }

}