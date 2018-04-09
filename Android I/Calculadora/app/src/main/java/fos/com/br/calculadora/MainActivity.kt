package fos.com.br.calculadora

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.TextView
import fos.com.br.calculadora.operacao.Expressao
import fos.com.br.calculadora.operacao.Numero
import fos.com.br.calculadora.operacao.Operacao
import fos.com.br.calculadora.operacao.Operacao.Companion.from
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    val ZERO: String = "0"
    val VIRGULA: String = ","

    /*  private var display = StringBuilder()
      private var operacaoTemporaria: StringBuilder = StringBuilder()
      private val listaOperacao = mutableListOf<Any>()*/

    private val listaOperacao = mutableListOf<String>()
    private val listener = OnClickListener { view ->
        val textView = view as TextView
        when (view.id) {
            R.id.txt_zero,
            R.id.txt_um,
            R.id.txt_dois,
            R.id.txt_tres,
            R.id.txt_quatro,
            R.id.txt_cinco,
            R.id.txt_seis,
            R.id.txt_sete,
            R.id.txt_oito,
            R.id.txt_nove,
            R.id.txt_divisao,
            R.id.txt_multiplicacao,
            R.id.txt_subtracao,
            R.id.txt_soma,
            R.id.txt_virgula -> addOperacao(textView)
            R.id.txt_ac -> limpar()
            R.id.txt_limpar_ultima_operacao -> limparUltimaOperacao()
            R.id.txt_igual -> calcularExpressao()
            else -> toast(message = "error")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /***
         * click listener numeros
         */
        txt_zero.setOnClickListener(listener)
        txt_um.setOnClickListener(listener)
        txt_dois.setOnClickListener(listener)
        txt_um.setOnClickListener(listener)
        txt_dois.setOnClickListener(listener)
        txt_tres.setOnClickListener(listener)
        txt_quatro.setOnClickListener(listener)
        txt_cinco.setOnClickListener(listener)
        txt_seis.setOnClickListener(listener)
        txt_sete.setOnClickListener(listener)
        txt_oito.setOnClickListener(listener)
        txt_nove.setOnClickListener(listener)
        txt_virgula.setOnClickListener(listener)

        /***
         * click listener operador
         */
        txt_divisao.setOnClickListener(listener)
        txt_multiplicacao.setOnClickListener(listener)
        txt_subtracao.setOnClickListener(listener)
        txt_soma.setOnClickListener(listener)

        /***
         * click listener função
         */
        txt_ac.setOnClickListener(listener)
        txt_limpar_ultima_operacao.setOnClickListener(listener)
        txt_igual.setOnClickListener(listener)

    }

    private fun addOperacao(button: TextView) {
        var valorAtual = button.text.toString()
        val operador = Operacao.from(valorAtual)



        valorAtual = concatenarNumeros(operador = operador, valorAtual = valorAtual)





        listaOperacao.add(valorAtual)

        toast(message = button.text.toString())

        // atualiza os dados no display da calculadora
        atualizaDisplay()
    }

    private fun concatenarNumeros(operador: Operacao?, valorAtual: String): String {
        var ultimoElementoLisOperacao = listaOperacao.lastOrNull()
        var valorTemp = valorAtual
        var limparUltimoElemento: Boolean = false

        if (ultimoElementoLisOperacao != null && operador == null && (
                        ultimoElementoLisOperacao.endsWith(VIRGULA)
                                || ultimoElementoLisOperacao.startsWith(VIRGULA)
                        )) {

            valorTemp = ultimoElementoLisOperacao + valorTemp
            limparUltimoElemento = true
        } else if (Operacao.from(ultimoElementoLisOperacao) == null && ultimoElementoLisOperacao != null && operador == null) {
            valorTemp = ultimoElementoLisOperacao + valorTemp
            limparUltimoElemento = true
        }

        if (limparUltimoElemento) {
            limparUltimaOperacao()
        }

        if(valorTemp == VIRGULA){
            valorTemp = ZERO + VIRGULA
        }

        return valorTemp
    }

    private fun atualizaDisplay() {
        val display = StringBuilder()
        listaOperacao.forEach { op -> display.append(op) }
        txt_display.setText(display)
    }

    private fun limpar() {
        listaOperacao.clear()
        addValorPadrao()
        atualizaDisplay()
    }

    private fun addValorPadrao() {
        if (listaOperacao.isEmpty()) {
            listaOperacao.add(ZERO)
        }
    }

    private fun limparUltimaOperacao() {
        if (listaOperacao.lastIndex >= 0) {
            listaOperacao.removeAt(listaOperacao.lastIndex)

        }

        atualizaDisplay()
    }

    private fun calcularExpressao() {
        var list = mutableListOf<Any>()

        var expressao: Expressao? = null
        val listaNumero = mutableListOf<Numero>()
        var operador: Operacao? = null

        listaOperacao.forEach { v ->
            val valor = if (v.contains(",")) v.replace(",", ".") else v
            val op = Operacao.from(valor)

            if (op == null) {
                listaNumero.add(Numero(valor.toDoubleOrNull()))
            } else {
                operador = op
            }

            if (listaNumero.size == 2 && operador != null) {
                expressao = operador?.calculo(listaNumero.get(0), listaNumero.get(1))
            } else if (expressao != null) {
                expressao = operador?.calculo(expressao!!, Numero(valor.toDoubleOrNull()))
            }

        }

        val result = expressao?.calcula().toString();
        listaOperacao.clear()
        listaOperacao.add(result.replace(".", ","))
        atualizaDisplay()
    }
}

