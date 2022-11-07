import org.json.JSONObject
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Path

fun main(args: Array<String>) {
    val path = "C:\\Users\\ciaoc\\OneDrive\\untitled3\\src\\main\\resources\\test.json"
    val json = JSONObject()

    var player = Player("NextDev")

    if(!Files.exists(Path.of("C:\\Users\\ciaoc\\OneDrive\\untitled3\\src\\main\\resources\\data"))) {
        Files.createDirectory(Path.of("C:\\Users\\ciaoc\\OneDrive\\untitled3\\src\\main\\resources\\data"))
    }

    val file = JsonFile("C:\\Users\\ciaoc\\OneDrive\\untitled3\\src\\main\\resources\\data", player.address)
    val map = mapOf(("player2" to player), ("bool2" to true))

    file.reload()

    player = file.getObject("player2", Player::class.java) as Player
    println(player.address)
    println(player.name)

    //file.create(map as HashMap<String, Any>)

    val file2 = JsonFile("C:\\Users\\ciaoc\\OneDrive\\untitled3\\src\\main\\resources\\data\\file")
    val defaults = hashMapOf<String, Any>(
        "a" to "1",
        "b" to "2",
        "c" to "3",
        "d" to "4",
        "e" to "5",
        "f" to "6"
    )

    if (!file2.exists()) {
        file2.create(defaults)
    }

    println(file2["a"])
}

class Player(var name: String):Serializable {
    var address: String = "95.234.208.189"
}

