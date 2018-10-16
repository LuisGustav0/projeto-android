package appinnovation.com.app_innovation.conexao

import android.os.AsyncTask
import appinnovation.com.app_innovation.model.Pessoa
import com.google.gson.Gson
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class HttpRestApiPost : AsyncTask<String, Void, Pessoa>() {

    override fun doInBackground(vararg params: String): Pessoa? {
        var httpURLConnection: HttpURLConnection? = null
        var bufferedReader: BufferedReader? = null

        try {
            val strJson = StringBuilder()
            var linha: String

            val url = URL(HTTP_POST)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "POST"
            httpURLConnection.setRequestProperty("Content-Type", "application/json")
            httpURLConnection.setRequestProperty("Accept", "application/json")

            val outputStreamWriter = OutputStreamWriter(httpURLConnection.outputStream)
            outputStreamWriter.write(params[0])
            outputStreamWriter.flush()

            val inputStream = httpURLConnection.inputStream
            bufferedReader = BufferedReader(InputStreamReader(inputStream))
            strJson.append(bufferedReader.readLine())

            return Gson().fromJson(strJson.toString(), Pessoa::class.java)
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

        return null
    }

    companion object {
        private val HTTP_POST = "http://192.168.101.43:8080/pessoa"
        private val SEPARATOR = File.separator
    }
}
