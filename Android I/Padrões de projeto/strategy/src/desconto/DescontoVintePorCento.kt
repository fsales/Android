package desconto

class DescontoVintePorCento : ICalculaDesconto {
    private val indiceDesconto = 0.2

    override fun valorDesconto(mensalidade: Double): Double = mensalidade * indiceDesconto
}