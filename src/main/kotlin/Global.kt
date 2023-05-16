@file:Suppress("FunctionName")

fun Any?.add(i: Int): Int {
    return this.int()+i
}

fun Any?.int() : Int{
    return this.toString().toDouble().toInt()
}

fun Any?.double() : Double{
    return this.toString().toDouble()
}

fun <T> T.println(): T {
    println(this)
    return this
}

fun <T> T.print(): T {
    print(this)
    return this
}

fun Any?.if_(predicate: (Any?) -> Boolean, function: (Any?) -> Any?): Any? {
    val result = predicate(this)
    return if(result) function(this) else this
}

fun String.getUntil(char: Char):String {
    val index = this.indexOf(char)
    if (index == -1) return this
    return this.substring(0, index)
}