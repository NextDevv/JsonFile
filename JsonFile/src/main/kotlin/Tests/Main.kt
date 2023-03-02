package Tests

import json.JsonFile
import yaml.YamlFile
import java.math.BigDecimal
import java.util.Properties

fun main(args: Array<String>) {
    println("------------------KOTLIN----------------")
    /*val f = File("C:\\Users\\ciaoc\\OneDrive\\Documenti\\Test Server 1.19.2\\plugins\\BraveAdmin")
    if(!f.exists()) f.createNewFile()

    val file = json(f.path+"\\test","config")
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

    /*val file = json("C:\\Users\\ciaoc\\OneDrive\\Desktop\\json\\src\\main\\resources", "test")
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

    val config = json["test"]!!
    val list = config.getList<Map>("maps")!!
    for(i in list.indices) {
        val map = list[i]
        println(map.name)
        println(map.location)
    }
    config.save()*/

    /*val file = json("C:\\Users\\ciaoc\\OneDrive\\Desktop\\json\\src\\main\\resources", "test4")
    if(!file.exists())
        file.create()
    val usernames = mutableListOf<String>("next", "nextdev", "spreest", "mrciao", "shadow", "bonky", "kazuho", "kazuho_dev", "kazuhoz")
    /*for(i in 0 .. 20) {
        val uuid = UUID.randomUUID().toString()
        file[uuid] = hashMapOf(
            "username" to usernames[Random().nextInt(usernames.size)],
            "uuid" to uuid,
            "wins" to Random().nextInt(100),
            "losses" to Random().nextInt(100),
        )
    }*/
    file["person"] = Person("giofornale@gmail.com", "Giovanni", "Gazzoli 94")
    file["person2"] = hashMapOf("email" to "something", "name" to "ok", "address" to "1")
    file.save()*/

    //val map = file["f54deb46-8384-498e-bb37-8bbe6faba30e"] as HashMap<String, Any>

    /*al person = f["person"] as Person
    println("Person: $person")
    val person2 = f.getObject("person2", Person::class.java)
    println("Person: $person2")

    println("19042e02-11dd-468c-9048-776c7720a9a4" in f)
    f.save()*/



    val json = JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\json\\src\\main\\resources", "main")
    if(!json.exists())
        json.create()
    json["person"] = Person("giofornale@gmail.com", "Giovanni", "Gazzoli 94")
    json["person2"] = hashMapOf("email" to "something", "name" to "e")
    json["int"] = 123
    json["big-decimal"] = BigDecimal("123.45")
    json["map"] = hashMapOf("Something" to "something")
    json.save()

    val jsonToYaml = json.toYamlFile("mainY")
}

fun String.getUntil(char: Char):String {
    val index = this.indexOf(char)
    if (index == -1) return this
    return this.substring(0, index)
}

data class Person(val email: String, val name: String, val address: String)
data class Test(var name:String)