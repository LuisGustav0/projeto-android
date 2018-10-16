package appinnovation.com.app_innovation.util

import android.widget.EditText
import java.lang.Long.parseLong

object UtilComponente {

    fun getValorEditTextLong(editText: EditText?): Long? {
        if (editText == null || Util.isValorNaoPreenchido(editText.text.toString())) {
            return null
        }

        val valor = editText.text.toString()

        return parseLong(valor)
    }
}
