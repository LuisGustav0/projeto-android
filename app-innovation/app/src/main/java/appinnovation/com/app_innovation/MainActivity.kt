package appinnovation.com.app_innovation

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*
import appinnovation.com.app_innovation.activity.PessoaActivity
import appinnovation.com.app_innovation.conexao.HttpRestApiDeletePorId
import appinnovation.com.app_innovation.conexao.HttpRestApiGet
import appinnovation.com.app_innovation.conexao.HttpRestApiPost
import appinnovation.com.app_innovation.model.Pessoa
import appinnovation.com.app_innovation.util.Util
import appinnovation.com.app_innovation.util.UtilAlert
import appinnovation.com.app_innovation.util.UtilComponente
import appinnovation.com.app_innovation.util.UtilToast
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private var edtCodigo: EditText? = null
    private var edtNome: EditText? = null
    private var checkBoxIsAtivo: CheckBox? = null
    private var btnSalvar: Button? = null
    private var btnLimpar: Button? = null
    private var btnExcluir: Button? = null
    private var listViewPessoa: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarComponentes()
        iniciarListeners()
        iniciarListViewPessoa()
    }

    private fun iniciarComponentes() {
        edtCodigo = findViewById<View>(R.id.edtCodigo) as EditText
        edtNome = findViewById<View>(R.id.edtNome) as EditText

        checkBoxIsAtivo = findViewById<View>(R.id.checkBoxIsAtivo) as CheckBox

        btnSalvar = findViewById<View>(R.id.btnSalvar) as Button
        btnLimpar = findViewById<View>(R.id.btnLimpar) as Button
        btnExcluir = findViewById<View>(R.id.btnExcluir) as Button

        listViewPessoa = findViewById<View>(R.id.listViewPessoa) as ListView
    }

    private fun onLimpar() {
        edtCodigo!!.setText("")
        edtNome!!.setText("")
        checkBoxIsAtivo!!.isChecked = false
    }

    private fun onSalvarPessoa() {
        val pessoa = Pessoa()
        pessoa.id = Util.getLong(edtCodigo!!.text)
        pessoa.nome = edtNome!!.text.toString()
        pessoa.ativo = checkBoxIsAtivo!!.isChecked

        if (Util.isValorNaoPreenchido(pessoa.nome!!)) {
            UtilToast.imprimirToast(this, "Campo preenchimento obrigatório: Nome");
            return;
        }

        val strJson = Gson().toJson(pessoa)

        try {
            val pessoaSalva = HttpRestApiPost().execute(strJson).get()
            if (pessoaSalva != null && Util.isValorPreenchido(pessoaSalva.id)) {
                edtCodigo!!.setText(pessoaSalva.id!!.toString())
                UtilToast.imprimirToast(this, "Pessoa salvo com sucesso!")
            } else {
                UtilToast.imprimirToast(this, "Não foi possivel salvar pessoa!")
            }

            iniciarListViewPessoa()
        } catch (ex: Exception) {
            UtilToast.imprimirToast(this, ex.message.toString())
        }

    }

    private fun deletarPessoaPorId() {
        val idPessoa = UtilComponente.getValorEditTextLong(edtCodigo)

        if (!Util.isValorPreenchido(idPessoa)) {
            UtilToast.imprimirToast(this, "Campo preenchimento orbigatorio: Código")
            return
        }

        HttpRestApiDeletePorId().execute(idPessoa)
        UtilToast.imprimirToast(this, "Pessoa excluída com sucesso!")
        onLimpar()
        iniciarListViewPessoa()
    }

    private fun onDeletarPessoa() {
        try {
            val alertBuilder = UtilAlert.getAlertConfirm(this, "Alerta", "Tem certeza que deseja deletar pessoa?")

            alertBuilder.setPositiveButton("Sim") { dialog, which -> deletarPessoaPorId() }

            alertBuilder.setNegativeButton("Não") { dialog, which -> dialog.dismiss() }

            alertBuilder.show()
        } catch (ex: Exception) {
            UtilToast.imprimirToast(this, ex.message.toString())
        }

    }

    private fun iniciarListeners() {
        btnSalvar!!.setOnClickListener { onSalvarPessoa() }

        btnLimpar!!.setOnClickListener { onLimpar() }

        btnExcluir!!.setOnClickListener { onDeletarPessoa() }
    }

    private fun onAbrirPessoaActivity(idPessoa: Long?) {
        val intentPessoaActivity = Intent(this@MainActivity, PessoaActivity::class.java)
        intentPessoaActivity.putExtra("idPessoa", idPessoa)
        startActivity(intentPessoaActivity)
    }

    private fun onPreencherPessoa(pessoa: Pessoa) {
        edtCodigo!!.setText(Util.getLongToString(pessoa.id))
        edtNome!!.setText(pessoa.nome)
        checkBoxIsAtivo!!.isChecked = pessoa.ativo!!;
    }

    private fun iniciarListViewPessoa() {
        try {
            val listaPessoa = HttpRestApiGet().execute().get()
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listaPessoa)
            listViewPessoa!!.adapter = adapter

            listViewPessoa!!.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                val pessoa = listViewPessoa!!.getItemAtPosition(position) as Pessoa
                //onAbrirPessoaActivity(pessoa.getId());
                onPreencherPessoa(pessoa)
            }

        } catch (ex: Exception) {
            UtilToast.imprimirToast(this, ex.message.toString())
        }

    }
}

