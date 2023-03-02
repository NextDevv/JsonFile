package yaml

import Exceptions.NoUrlFoundException
import org.yaml.snakeyaml.Yaml
import utils.Url
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class YamlUrl(private var url: String) {
    private val yaml = Yaml()
    private var content:HashMap<String, Any?> = hashMapOf()

    init {
        if(!url.startsWith("http://") || !url.startsWith("https://"))
            url = "https://$url"

        val string = Url.readUrl(url)
            ?: throw NoUrlFoundException("The specified URL is not valid: \n\t ERROR -> [ $url ]")

        content = yaml.load(string)
    }

    operator fun get(key: String): Any? {
        return content[key]
    }

    operator fun set(key: String, value: Any?) {
        content[key] = value
    }

    operator fun <T> get(key: String, clazz: Class<T>): T {
        return clazz.cast(content[key])
    }

    fun getString(key: String): String {
        return content[key] as String
    }

    fun getBoolean(key: String): Boolean {
        return content[key] as Boolean
    }

    fun getInteger(key: String): Int {
        return content[key] as Int
    }

    fun getLong(key: String): Long {
        return content[key] as Long
    }

    fun getFloat(key: String): Float {
        return content[key] as Float
    }

    fun getDouble(key: String): Double {
        return content[key] as Double
    }

    fun <T> getArray(key: String): Array<T> {
        return content[key] as Array<T>
    }

    fun <K,V> getHashMap(key: String): HashMap<K,V> {
        return content[key] as HashMap<K,V>
    }

    fun <T> getObject(key: String): T {
        return content[key] as T
    }

    fun toYamlFile(parent:String, name:String): YamlFile {
        val yaml = YamlFile(parent, name)
        yaml.addAll(content)
        return yaml
    }
}