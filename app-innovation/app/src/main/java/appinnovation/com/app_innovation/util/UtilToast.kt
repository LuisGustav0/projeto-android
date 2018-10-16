package appinnovation.com.app_innovation.util

import android.app.Activity
import android.widget.Toast

object UtilToast {

    fun imprimirToast(activity: Activity, strMensagem: String) {
        Toast.makeText(activity, strMensagem, Toast.LENGTH_SHORT).show()
    }
}
