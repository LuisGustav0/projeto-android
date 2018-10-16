package appinnovation.com.app_innovation.util

import android.app.Activity
import android.app.AlertDialog

object UtilAlert {

    fun getAlertConfirm(activity: Activity, titulo: String, mensagemConfirmar: String): AlertDialog.Builder {
        val alertBuilder = AlertDialog.Builder(activity)
        alertBuilder.setTitle(titulo)
        alertBuilder.setMessage(mensagemConfirmar)

        return alertBuilder
    }
}
