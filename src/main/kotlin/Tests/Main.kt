package Tests

import com.fasterxml.jackson.core.JsonFactoryBuilder
import json.JsonBuilder
import json.JsonFile
import println

fun main(args: Array<String>) {
    println("------------------KOTLIN----------------")
    val json = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources\\link.json")
        .load()

    val j = JsonBuilder().toJson(mapOf("fuck" to "OK", "ts" to 123,"double" to 1.1, "bool" to true))
    JsonBuilder().fromJson<String, String>(j).println()

    with(json) {
        save()
    }
}



data class Person(val email: String, val name: String, val address: String)
data class Test(var name:String)