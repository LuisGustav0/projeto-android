package appinnovation.com.app_innovation.conexao

import android.os.AsyncTask
import appinnovation.com.app_innovation.util.Util
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class HttpRestApiDeletePorId : AsyncTask<Long, Void, Boolean>() {

    override fun doInBackground(vararg params: Long?): Boolean {
        var httpDelete = ""

        if (params != null && Util.isValorPreenchido(params[0])) {
            val idPessoa = params[0]
            httpDelete = HTTP_DELETE + SEPARATOR + idPessoa
        }

        var httpURLConnection: HttpURLConnection? = null

        try {
            val linha: String

            val url = URL(httpDelete)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "DELETE"
            httpURLConnection.connect()

            if (httpURLConnection.responseCode == HttpURLConnection.HTTP_OK) {
                httpURLConnection?.disconnect()
            }

            return true
        } catch (ex: Exception) {
            httpURLConnection?.disconnect()
        }

        return false
    }

    companion object {

        private val HTTP_DELETE = "http://192.168.101.43:8080/pessoa"
        private val SEPARATOR = File.separator
    }
}
