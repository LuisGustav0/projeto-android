package appinnovation.com.app_innovation.model

class Pessoa {
    var id: Long? = null
    var nome: String? = null
    var ativo: Boolean? = null

    val isInativo: Boolean
        get() = !(this.ativo!!)

    override fun toString(): String {
        return id.toString() + " - " + nome
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + if (id == null) 0 else id!!.hashCode()
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj)
            return true
        if (obj == null)
            return false
        if (javaClass != obj.javaClass)
            return false
        val other = obj as Pessoa?
        if (id == null) {
            if (other!!.id != null)
                return false
        } else if (id != other!!.id)
            return false
        return true
    }
}
