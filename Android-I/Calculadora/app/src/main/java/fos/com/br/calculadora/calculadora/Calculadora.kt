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

        fun numero() = ""
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

        val mapNumero = mutableMapOf<Int, Numero>()
        val mapOperador = mutableMapOf<Int, Operacao>()

        listaOperacao.forEachIndexed { index, v ->
            val valor = if (v.contains(",")) v.replace(",", ".") else v
            val op = Operacao.from(valor)
            if (op == null && valor.toDoubleOrNull() != null) {
                mapNumero.put(index, Numero(valor.toDouble()))
            } else if (op != null) {
                mapOperador.put(index, op)
            }
        }
        var expressao: Expressao? = null
        mapOperador.forEach { key, value ->
            if (expressao == null) {
                expressao = value.calculo(mapNumero[key - 1]!!, mapNumero[key + 1]!!)
            } else {
                expressao = value.calculo(Numero(expressao!!.calcula()), mapNumero[key + 1]!!)
            }
        }

        return expressao!!.calcula()
    }
}


