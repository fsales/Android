package fos.com.br.calculadora.calculadora

class Numero(val numero: Double?) : Expressao {

    override fun calcula() = numero

}