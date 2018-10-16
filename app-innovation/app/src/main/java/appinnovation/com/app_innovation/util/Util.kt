package appinnovation.com.app_innovation.util

import java.math.BigDecimal

object Util {

    fun isValorPreenchido(valor: Any?): Boolean {
        return (valor != null &&
                !valor.equals("") &&
                !(valor is Double && valor.isNaN())
                && !(valor is Float && valor.isNaN())
                && !(valor is BigDecimal && valor.compareTo(BigDecimal.ZERO) == 0))
    }

    fun isValorNaoPreenchido(valor: Any): Boolean {
        return !isValorPreenchido(valor)
    }

    fun getLong(numero: Any?): Long? {
        if (numero == null) {
            return null
        }

        try {
            if (numero is Number) {
                return numero.toLong()
            }
            val numeroDouble = java.lang.Double.parseDouble(numero.toString())
            return numeroDouble.toLong()
        } catch (e: Exception) {
            return null
        }
    }

    fun getValorBoolean(valor: Any?): Boolean {
        return valor != null && (valor.toString().equals("S", ignoreCase = true) || valor.toString().equals("Sim", ignoreCase = true) ||
                        valor.toString().equals("true", ignoreCase = true) || valor.toString().equals("t", ignoreCase = true) ||
                        valor.toString().equals("1", ignoreCase = true))
    }

    fun getLongToString(id: Long?): String {
        return if (isValorPreenchido(id)) {
            id!!.toString()
        } else ""
    }
}
