

interface ICoquetel{
    fun prepararCoquetel()
}

class CoquetelBase : ICoquetel{
    override fun prepararCoquetel() {
        System.out.println("teste")
    }

}

open abstract  class CoquetelDecorator: ICoquetel{

    var coquetel:ICoquetel

    constructor( coquetel:ICoquetel){
        this.coquetel = coquetel
    }
    override fun prepararCoquetel() {
        coquetel.prepararCoquetel()
        System.out.println("coquetel decorator")
    }

}


class Coquetel : CoquetelDecorator {
    constructor(coquetel: ICoquetel) : super(coquetel)

     override fun prepararCoquetel() {
        super.prepararCoquetel()
         System.out.print("coquetel")
    }
}

fun main(args: Array<String>) {
    val coquetelBase = CoquetelBase()
    coquetelBase.prepararCoquetel()

    val coquetel = Coquetel(coquetelBase)
    coquetel.prepararCoquetel()

}