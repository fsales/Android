package fos.com.br.calculadora.calculadora

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
    Porcentagem("%") {
        override fun calculo(expressao1: Expressao, expressao2: Expressao): Expressao = object : Expressao {
            override fun calcula(): Double {

                return Operacao.Multiplicacao.calculo(expressao1, Operacao.Divisao.calculo(expressao2, Numero(100.00))).calcula()!!
            }
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

class Calculadora {

    fun executarOperacao(listaOperacao: List<String>): Double? {
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

        return expressao?.calcula()
    }
}