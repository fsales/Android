package br.com.e_aluno.model

data class Aluno(val nome: String,
                 val matricula: String,
                 val telefone: String,
                 val usuario: Usuario?) {
    constructor() : this("", "", "", null)
}