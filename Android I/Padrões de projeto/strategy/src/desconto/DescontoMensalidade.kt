package desconto

class MensalidadeExAluno : IDescontoMensalidade {

    override fun valorMensalidade(mensalidade: Double): Double = mensalidade - DescontoVintePorCento().valorDesconto(mensalidade)
}

class MensalidadeNovoAluno : IDescontoMensalidade {

    override fun valorMensalidade(mensalidade: Double): Double = mensalidade
}