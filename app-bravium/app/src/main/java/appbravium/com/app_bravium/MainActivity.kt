package appbravium.com.app_bravium

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private var btnPortalAluno: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        iniciarComponente()
        iniciarListeners()
    }

    private fun iniciarComponente() {
        btnPortalAluno = findViewById<View>(R.id.btnPortalAluno) as Button
    }

    private fun onAbrirWebViewPortalAluno() {
        val intent = Intent(this@MainActivity, WebViewPortalAlunoActivity::class.java)
        intent.data = Uri.parse("https://sistema.bravium.net/alunos/lespem")
        startActivity(intent)
    }

    private fun iniciarListeners() {
        btnPortalAluno!!.setOnClickListener {
            onAbrirWebViewPortalAluno()
        }
    }
}
