package Tests

import JsonFile.JsonFile
import com.google.gson.GsonBuilder
import com.squareup.okhttp.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

fun main(args: Array<String>) {
    println("------------------KOTLIN----------------")
    /*val f = File("C:\\Users\\ciaoc\\OneDrive\\Documenti\\Test Server 1.19.2\\plugins\\BraveAdmin")
    if(!f.exists()) f.createNewFile()

    val file = JsonFile(f.path+"\\test","config")
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

    file.reload()*/

    /*val file = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources", "test")
    val hash = hashMapOf<String, Any?>(
        "debug" to false,
        "game_type" to "solo",
        "max_teams" to 10,
        "max_players" to 10,
        "max_players_per_team" to 2,
        "maps" to listOf<Map>(
            Map("map_1", "3213921"),
            Map("map_2", "3213921"),
            Map("map_3", "3213921")
        ),
        "host" to "localhost",
        "port" to 8081,
        "name" to "Awit_Wars",
        "password" to "password",
        "username" to "username",
        "can_admin_be_kicked" to true
    )
    if(!file.exists())
        file.create(hash)

    val config = JsonFile["test"]!!
    val list = config.getList<Map>("maps")!!
    for(i in list.indices) {
        val map = list[i]
        println(map.name)
        println(map.location)
    }
    config.save()*/

    val file = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources", "test")
    if(!file.exists())
        file.create()
    file["int"] = 1

    println(file["int"])

    file.save()
}

fun String.getUntil(char: Char):String {
    val index = this.indexOf(char)
    if (index == -1) return this
    return this.substring(0, index)
}

data class Person(val email: String, val name: String, val address: String)
data class Test(var name:String)