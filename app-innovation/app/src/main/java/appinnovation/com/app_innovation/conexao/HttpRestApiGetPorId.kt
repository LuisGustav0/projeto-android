package appinnovation.com.app_innovation.conexao

import android.os.AsyncTask
import appinnovation.com.app_innovation.model.Pessoa
import appinnovation.com.app_innovation.util.Util
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class HttpRestApiGetPorId : AsyncTask<Long, Void, Pessoa>() {

    override fun doInBackground(vararg params: Long?): Pessoa? {
        var httpGet = ""

        if (params != null && Util.isValorPreenchido(params[0])) {
            val idPessoa = params[0]
            httpGet = HTTP_GET + SEPARATOR + idPessoa
        }

        var httpURLConnection: HttpURLConnection? = null
        var bufferedReader: BufferedReader? = null

        try {
            val strJson = StringBuilder()
            var linha: String

            val url = URL(httpGet)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.connect()

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
        private val HTTP_GET = "http://192.168.101.43:8080/pessoa"
        private val SEPARATOR = File.separator
    }
}
