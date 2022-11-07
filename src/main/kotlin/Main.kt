import org.json.JSONObject
import java.io.File
import java.io.Serializable
import java.nio.file.Files
import java.nio.file.Path
import java.time.LocalDate
import java.util.concurrent.TimeUnit

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


}

class Player(var name: String):Serializable {
    var address: String = "95.234.208.189"
}

