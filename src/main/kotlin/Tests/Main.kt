package Tests

import com.fasterxml.jackson.core.JsonFactoryBuilder
import json.JsonBuilder
import json.JsonFile
import json.JsonObject
import println
import java.time.Period

fun main(args: Array<String>) {
    println("------------------KOTLIN----------------")


    val file = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources\\file.json")
        .load()
    val jsonObject = JsonObject(Person("test@ts.com", "John Doe", "New York"))
    file.getObject("person_2").fromJson<Person>().println()

    file.save()
}



data class Person(val email: String, val name: String, val address: String)
data class Test(var name:String)