package json

class JsonNull {
    companion object {
        @JvmStatic
        fun getNull(): String {
            return "{ \"error\": \"null\" }"
        }
    }
}