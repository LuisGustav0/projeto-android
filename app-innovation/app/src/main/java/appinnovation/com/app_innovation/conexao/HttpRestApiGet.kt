package appinnovation.com.app_innovation.conexao

import android.os.AsyncTask
import appinnovation.com.app_innovation.model.Pessoa
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

class HttpRestApiGet : AsyncTask<Void, Void, List<Pessoa>>() {

    override fun doInBackground(vararg params: Void): List<Pessoa> {
        var httpURLConnection: HttpURLConnection? = null
        var bufferedReader: BufferedReader? = null

        try {
            val strJson = StringBuilder()

            val url = URL(HTTP_GET)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()

            val inputStream = httpURLConnection.inputStream
            bufferedReader = BufferedReader(InputStreamReader(inputStream))

            strJson.append(bufferedReader.readLine())

            val listType = object : TypeToken<ArrayList<Pessoa>>() {}.type
            val pessoas: List<Pessoa> = Gson().fromJson(strJson.toString(), listType) as List<Pessoa>

            return pessoas;
        } catch (ex: Exception) {
            httpURLConnection?.disconnect()

            if (bufferedReader != null) {
                try {
                    bufferedReader.close()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }

            }
        }

        return ArrayList()
    }

    companion object {
        private val HTTP_GET = "http://192.168.101.43:8080/pessoa"
        private val SEPARATOR = File.separator
    }
}
