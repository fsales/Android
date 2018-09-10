package br.com.e_aluno.extension

import br.com.e_aluno.R
import br.com.e_aluno.fragment.NoticiasFragment
import br.com.e_aluno.model.Noticia

fun NoticiasFragment.mockNoticias(): List<Noticia> {

    val not1 = Noticia(
            id = 1,
            descricaoCurta = "Quem nunca teve de tratar um NullPointerException? O sistema de tipos do Kotlin distingue...",
            descricao = "Quem nunca teve de tratar um NullPointerException? O sistema de tipos do Kotlin distingue as referências que podem ou não ser nulas:\n" +
                    "\n" +
                    "var name: String = \"Kotlin\"\n" +
                    "name = null // error\n" +
                    "var name: String? = \"Kotlin\"\n" +
                    "name = null // is ok!\n" +
                    "Note que no tipo String com ? pode ser atribuido valor nulo, e poderemos fazer chamadas seguras:\n" +
                    "\n" +
                    "name?.length\n" +
                    "Se name for null a chamada ao método length não será feita, mas podemos também forçar essa chamada:\n" +
                    "\n" +
                    "name!!.length\n" +
                    "Dessa forma, se name for null teremos uma exception explicita que deverá ser tratada." +
                    "Similar em outras linguagens, Kotlin pode declarar métodos em tipos que você não controla , por exemplo:\n" +
                    "\n" +
                    "fun String.hello(): String{\n" +
                    " return “Hello ${this}”\n" +
                    "}\n" +
                    "\n" +
                    "val x = \"Kotlin\"\n" +
                    "print(x.hello()) // prints \"Hello Kotlin\"\n" +
                    "Nesse caso foi declarado um método dentro do tipo String, um exemplo mais adentro do mundo Android seria esse:\n" +
                    "\n" +
                    "fun ImageView.load(url: String){\n" +
                    "  Picasso.with(context).load(url).into(this)\n" +
                    "}\n" +
                    "Agora qualquer ImageView pode chamar o método load e carregar uma imagem passando uma url.\n" +
                    "\n" +
                    "Extensions trazem um grande ganho em legibilidade, pois evita que tenhamos de escrever os famigerados Utils (StringUtils, FileUtils , etc), que com certeza todo dev Android já deve ter escrito algum dia.",
            titulo = "Null Safety",
            codigoImagem = R.drawable.trem)
    val not2 = Noticia(
            id = 2,
            descricaoCurta = "Similar em outras linguagens, Kotlin pode declarar métodos em tipos que você não controla...",
            descricao = "Similar em outras linguagens, Kotlin pode declarar métodos em tipos que você não controla , por exemplo:\n" +
                    "\n" +
                    "fun String.hello(): String{\n" +
                    " return “Hello ${this}”\n" +
                    "}\n" +
                    "\n" +
                    "val x = \"Kotlin\"\n" +
                    "print(x.hello()) // prints \"Hello Kotlin\"\n" +
                    "Nesse caso foi declarado um método dentro do tipo String, um exemplo mais adentro do mundo Android seria esse:\n" +
                    "\n" +
                    "fun ImageView.load(url: String){\n" +
                    "  Picasso.with(context).load(url).into(this)\n" +
                    "}\n" +
                    "Agora qualquer ImageView pode chamar o método load e carregar uma imagem passando uma url.\n" +
                    "\n" +
                    "Extensions trazem um grande ganho em legibilidade, pois evita que tenhamos de escrever os famigerados Utils (StringUtils, FileUtils , etc), que com certeza todo dev Android já deve ter escrito algum dia.",
            titulo = "Extension Methods",
            codigoImagem = R.drawable.pessoas)
    val not3 = Noticia(
            id = 3,
            descricaoCurta = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são...",
            descricao = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são por questões de design, " +
                    "para que tenhamos um código mais limpo.\n" +
                    "As classes em Kotlin são final por padrão (por conta do design da linguagem). Desta forma, elas não podem ser extendidas, obrigando " +
                    "que o desenvolvedor cuide melhor de classes que servem a este propósito.\n" +
                    "class Student{\n... //não pode ser extendida\n}\n" +
                    "open class Person{\n" +
                    " ...//pode ser extendida\n" +
                    "}\n" +
                    "Atributos também tiveram algumas mudanças. Usamos var e val para declarar atributos, mas forma de declarar os tipos também mudou. Os tipos não são mais obrigatórios, ou seja, o compilador consegue inferir o tipo na maioria dos casos. Exemplo:\n" +
                    "\n" +
                    "var name: String = \"Kotlin\" // após o nome do atributo declaramos o tipo\n" +
                    "var name = \"Kotlin\" // Caso o tipo não seja declarado o compilador vai inferir o tipo",
            titulo = "Algumas mudanças",
            codigoImagem = R.drawable.senhores)

    val not4 = Noticia(
            id = 4,
            descricaoCurta = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são...",
            descricao = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são por questões de design, " +
                    "para que tenhamos um código mais limpo.\n" +
                    "As classes em Kotlin são final por padrão (por conta do design da linguagem). Desta forma, elas não podem ser extendidas, obrigando " +
                    "que o desenvolvedor cuide melhor de classes que servem a este propósito.\n" +
                    "class Student{\n... //não pode ser extendida\n}\n" +
                    "open class Person{\n" +
                    " ...//pode ser extendida\n" +
                    "}\n" +
                    "Atributos também tiveram algumas mudanças. Usamos var e val para declarar atributos, mas forma de declarar os tipos também mudou. Os tipos não são mais obrigatórios, ou seja, o compilador consegue inferir o tipo na maioria dos casos. Exemplo:\n" +
                    "\n" +
                    "var name: String = \"Kotlin\" // após o nome do atributo declaramos o tipo\n" +
                    "var name = \"Kotlin\" // Caso o tipo não seja declarado o compilador vai inferir o tipo",
            titulo = "Algumas mudanças",
            codigoImagem = R.drawable.senhores)


    val not5 = Noticia(
            id = 5,
            descricaoCurta = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são...",
            descricao = "Durante criação da linguagem o time tomou algumas decisões que eu achei bem interessante, imagino eu que são por questões de design, " +
                    "para que tenhamos um código mais limpo.\n" +
                    "As classes em Kotlin são final por padrão (por conta do design da linguagem). Desta forma, elas não podem ser extendidas, obrigando " +
                    "que o desenvolvedor cuide melhor de classes que servem a este propósito.\n" +
                    "class Student{\n... //não pode ser extendida\n}\n" +
                    "open class Person{\n" +
                    " ...//pode ser extendida\n" +
                    "}\n" +
                    "Atributos também tiveram algumas mudanças. Usamos var e val para declarar atributos, mas forma de declarar os tipos também mudou. Os tipos não são mais obrigatórios, ou seja, o compilador consegue inferir o tipo na maioria dos casos. Exemplo:\n" +
                    "\n" +
                    "var name: String = \"Kotlin\" // após o nome do atributo declaramos o tipo\n" +
                    "var name = \"Kotlin\" // Caso o tipo não seja declarado o compilador vai inferir o tipo",
            titulo = "Algumas mudanças",
            codigoImagem = R.drawable.senhores)

    return kotlin.collections.arrayListOf<Noticia>(not1, not2, not3, not4, not5)
}