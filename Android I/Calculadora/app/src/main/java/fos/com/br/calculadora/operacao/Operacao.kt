package fos.com.br.calculadora.operacao

import fos.com.br.calculadora.operacao.Operacao.*

enum class Operacao(val operador: String) {
    Soma("+") {
        override fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao = object : Expressao {
            override fun calcula(): Double = expressao1.calcula()!!.plus(expressao2.calcula()!!)
        }
    },
    Subtracao("-") {
        override fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao = object : Expressao {
            override fun calcula(): Double = expressao1.calcula()!!.minus(expressao2.calcula()!!)
        }
    },
    Multiplicacao(operador = "X") {
        override fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao = object : Expressao {

            override fun calcula(): Double = expressao1.calcula()!!.times(expressao2.calcula()!!)
        }
    },
    Divisao("/") {
        override fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao = object : Expressao {
            override fun calcula(): Double {
                return expressao1.calcula()!!.div(expressao2.calcula()!!)
            }
        }
    };

    abstract fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao

    companion object {
        fun from(findValue: String?): Operacao? {
            return Operacao.values().singleOrNull({ ope -> ope.operador == findValue })
        }
    }
}