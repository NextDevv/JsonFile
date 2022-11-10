package Tests

import JsonFile.JsonFile
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    println("------------------KOTLIN----------------")
    val file = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources\\folder","config")
    val defaults =
        hashMapOf<String, Any?>("version" to "1.0.0", "debug" to true, "levels" to 2)
    if(!file.exists()) file.create(defaults)

    println("Version: ${file["version"]}")
    println("Debug: ${file["debug"]}")
    println("Levels: ${file["levels"]}")

    file["Person"] = Person("john.doe@example.com", "John Doe", "new York City 123")

    val person = file.getObject("Person", Person::class.java) as Person

    println(file.isInstance("Person", Person::class.java))

    println(person.name)
    println(person.email)
    println(person.address)

    file.reload()


}

data class Person(val email: String, val name: String, val address: String)