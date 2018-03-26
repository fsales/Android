import desconto.DescontoExAluno
import desconto.DescontoNovoAluno
import enuns.SituacaoAluno
import vo.Aluno

class MensalidadeSemDesconto {
    private val valor = 700.00

    fun calcular(aluno: Aluno): Double = if (SituacaoAluno.Ex.equals(aluno.situacao)){
         valor * 0.8
     }else {
         valor
     }
}

fun main(args: Array<String>) {
    val mensalidade = MensalidadeSemDesconto()
    val valorExAluno = mensalidade.calcular(Aluno("Fábio", SituacaoAluno.Ex))
    println(valorExAluno)

    val valorNovoAluno = mensalidade.calcular(Aluno("Fábio", SituacaoAluno.Novo))
    println(valorNovoAluno)
}