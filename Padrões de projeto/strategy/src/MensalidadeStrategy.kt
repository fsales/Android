import enuns.SituacaoAluno
import vo.Aluno
import desconto.DescontoExAluno
import desconto.DescontoNovoAluno

class MensalidadeStrategy {
    private val valor = 700.00

    fun calcular(aluno: Aluno): Double {
        val desconto = if (SituacaoAluno.Ex.equals(aluno.situacao)) {
            DescontoExAluno()
        } else {
            DescontoNovoAluno()
        }

        return valor - desconto.calcularValor(valor)
    }
}

fun main(args: Array<String>) {
   val mensalidade = MensalidadeStrategy()
   val valorExAluno = mensalidade.calcular(Aluno("Fábio",SituacaoAluno.Ex))
    println(valorExAluno)

    val valorNovoAluno = mensalidade.calcular(Aluno("Fábio",SituacaoAluno.Novo))
    println(valorNovoAluno)
}