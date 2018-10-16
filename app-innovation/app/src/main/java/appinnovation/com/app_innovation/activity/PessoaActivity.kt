package appinnovation.com.app_innovation.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import appinnovation.com.app_innovation.MainActivity
import appinnovation.com.app_innovation.R
import appinnovation.com.app_innovation.conexao.HttpRestApiGetPorId
import appinnovation.com.app_innovation.util.Util
import appinnovation.com.app_innovation.util.UtilToast

class PessoaActivity : AppCompatActivity() {

    private var edtCodigo: EditText? = null
    private var edtNome: EditText? = null
    private var btnVoltarMainActivity: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pessoa)

        iniciarComponentes()
        iniciarListeners()

        getParametroMainActivity()
    }

    private fun iniciarComponentes() {
        edtCodigo = findViewById<View>(R.id.edtCodigo) as EditText
        edtNome = findViewById<View>(R.id.edtNome) as EditText

        btnVoltarMainActivity = findViewById<View>(R.id.btnVoltarMainActivity) as Button
    }

    private fun onVoltarMainActivity() {
        val intentMainActivity = Intent(this@PessoaActivity, MainActivity::class.java)
        startActivity(intentMainActivity)
        this.finish()
    }

    private fun iniciarListeners() {
        btnVoltarMainActivity!!.setOnClickListener { onVoltarMainActivity() }
    }

    private fun preencherPessoa(idPessoa: Long?) {
        try {
            val pessoa = HttpRestApiGetPorId().execute(idPessoa).get()
            if (pessoa != null) {
                edtCodigo!!.setText(if (Util.isValorPreenchido(pessoa.id)) pessoa.id!!.toString() else "")
                edtNome!!.setText(pessoa.nome)
            } else {
                onVoltarMainActivity()
            }
        } catch (ex: Exception) {
            UtilToast.imprimirToast(this, ex.message.toString())
        }

    }

    private fun getParametroMainActivity() {
        val intent = intent
        val idPessoa = intent.getLongExtra("idPessoa", 0L)

        preencherPessoa(idPessoa)
    }
}
