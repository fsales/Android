package fos.com.br.calculadora.operacao

class Numero(val numero: Double?) : Expressao {

    override fun calcula() = numero

}