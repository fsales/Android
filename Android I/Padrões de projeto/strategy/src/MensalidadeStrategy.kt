import enuns.SituacaoAluno
import vo.Aluno
import desconto.MensalidadeExAluno
import desconto.MensalidadeNovoAluno

class MensalidadeStrategy {
    private val valor = 700.00

    fun calcular(aluno: Aluno): Double {
        return if (SituacaoAluno.Ex.equals(aluno.situacao)) {
            MensalidadeExAluno().valorMensalidade(valor)
        } else {
            MensalidadeNovoAluno().valorMensalidade(valor)
        }
    }
}

fun main(args: Array<String>) {
   val mensalidade = MensalidadeStrategy()
   val valorExAluno = mensalidade.calcular(Aluno("Fábio",SituacaoAluno.Ex))
    println(valorExAluno)

    val valorNovoAluno = mensalidade.calcular(Aluno("Fábio",SituacaoAluno.Novo))
    println(valorNovoAluno)
}