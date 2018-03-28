package desconto

class DescontoExAluno : IDEsconto {

    private val indiceDesconto = 0.2

    override fun calcularValor(mensalidade: Double): Double = mensalidade * indiceDesconto
}

class DescontoNovoAluno : IDEsconto {

    override fun calcularValor(mensalidade: Double): Double = 0.0
}