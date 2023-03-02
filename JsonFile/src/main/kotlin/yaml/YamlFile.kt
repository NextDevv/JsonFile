package yaml

import json.JsonFile
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileNotFoundException
import java.math.BigDecimal
import java.math.BigInteger
import java.time.*

@Suppress("unused", "MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
/**
 * Simple way to create YAML files and read them.
 * @see YamlUrl
 * @author NextDev
 */
class YamlFile(private var name: String) {
    private var map = HashMap<String, Any?>()
    private var parent = ""
    private var om = ObjectMapper(YAMLFactory())
    private val yaml = Yaml()

    constructor(parent: String, name: String) : this(name) {
        this.parent = parent
    }

    init {
        if(!name.contains(".yml") || !name.contains(".yaml"))
            name = "$name.yml"
        if(exists()) {
            load()
        }
    }

    /**
     * Adds a new key-value pair to the file.
     * @param map The map to add.
     */
    fun addAll(map: Map<String, Any?>) {
        this.map.putAll(map)
    }

    /**
     * Loads the yaml file, that is loaded when created the class
     */
    fun load() {
        val file = File(parent, name)
        if(!file.exists()) {
            throw FileNotFoundException("File $name does not exist")
        }
        val inputStream = file.inputStream()
        map = yaml.load(inputStream)
    }

    operator fun contains(key: String): Boolean {
        return map.containsKey(key)
    }

    override operator fun equals(other: Any?): Boolean {
        if(other == null) return false
        if(this === other) return true
        if(javaClass!= other.javaClass) return false
        return try {
            val clazz = other as YamlFile
            val path = clazz.parent+System.getProperty("file.separator")+clazz.name
            if(path == this.parent+System.getProperty("file.separator")+this.name) {
                return true
            }
            false
        }catch (e: Exception) {
            false
        }
    }

    /**
     * Create a new yaml
     * @param content the content of the file
     * @return a new yaml
     */
    fun create(content: Map<String, Any?>): YamlFile {
        if(!name.contains(".yml") || !name.contains(".yaml"))
            name = "$name.yml"
        val file = File(parent, name)
        if(file.exists())
            throw FileAlreadyExistsException(file, null, "The YAML file already exists.")
        file.createNewFile()
        om.writeValue(file, content)
        map = content as HashMap<String, Any?>
        return this
    }

    /**
     * Overwrite the content of the file with the given map.
     * @param content the content to be written
     * @return this yaml
     */
    fun overwrite(content: Map<String, Any?>): YamlFile {
        if(!name.contains(".yml") ||!name.contains(".yaml"))
            name = "$name.yml"
        val file = File(parent, name)
        if(file.exists()) {
            file.writeText("")
        }else throw FileNotFoundException("The YAML file does not exist.")
        om.writeValue(file, content)
        map = content as HashMap<String, Any?>
        return this
    }

    /**
     * Checks if the file exists.
     */
    fun exists(): Boolean {
        val file = File(parent, name)
        return file.exists()
    }

    /**
     * Gets the content of the file.
     * @return the content of the file
     */
    operator fun get(key: String): Any? {
        val args = key.split(".")
        var k:Any? = null
        args.forEach {
            if(k is Map<*, *>)
                k = (k as Map<String, Any?>)[it]
            else if(map[it] is Map<*,*>)
                k = map[it]
        }
        return k
    }

    /**
     * Sets the content of the file.
     * @param key the key to set
     * @param value the value to set
     * @return The previous content of the file
     */
    operator fun set(key: String, value: Any?):Any? {
        val previous = map[key]
        map[key] = value
        return previous
    }

    /**
     * Saves the file.
     */
    fun save() {
        val file = File(parent, name)
        if(!file.exists())
            file.createNewFile()
        file.writeText("")
        om.writeValue(file, map)
    }

    /**
     * Gets a map from the file.
     * @param key the key to get
     * @return The map found
     */
    fun getMap(key: String): Map<String, Any?> {
        return map[key] as Map<String, Any?>
    }

    /**
     * Gets a list from the file.
     * @param key the key to get
     * @return The list found
     */
    fun <T> getList(key: String): List<T> {
        return map[key] as List<T>
    }

    fun getString(key: String): String {
        return map[key] as String
    }

    fun getBoolean(key: String): Boolean {
        return map[key] as Boolean
    }

    fun getInteger(key: String): Int {
        return map[key] as Int
    }

    fun getLong(key: String): Long {
        return map[key] as Long
    }

    fun getDouble(key: String): Double {
        return map[key] as Double
    }

    fun getFloat(key: String): Float {
        return map[key] as Float
    }

    fun getBigDecimal(key: String): BigDecimal {
        return map[key] as BigDecimal
    }

    fun getBigInteger(key: String): BigInteger {
        return map[key] as BigInteger
    }

    fun getShort(key: String): Short {
        return map[key] as Short
    }

    fun getByte(key: String): Byte {
        return map[key] as Byte
    }

    fun getInstant(key: String): Instant {
        return map[key] as Instant
    }

    fun getLocalDate(key: String): LocalDate {
        return map[key] as LocalDate
    }

    fun getLocalTime(key: String): LocalTime {
        return map[key] as LocalTime
    }

    fun getLocalDateTime(key: String): LocalDateTime {
        return map[key] as LocalDateTime
    }

    fun getZonedDateTime(key: String): ZonedDateTime {
        return map[key] as ZonedDateTime
    }

    /**
     * Transforms the YAML file to a [JsonFile] without any errors
     *
     * @param name The name you want to give to the file
     * @param save If the file should be saved or not
     * @param replace If the file should be replaced or not, if it already exists
     * @return The [JsonFile]
     */
    fun toJsonFile(name: String = this.name, save:Boolean = true, replace:Boolean = true): JsonFile {
        val jsonFile = JsonFile(parent, name.replace(".yml", "").replace(".yaml", ""))
        if(jsonFile.exists() && !replace)
            throw FileAlreadyExistsException(jsonFile.getFile()!!)
        else if(jsonFile.exists() && replace) {
            jsonFile.delete()
        }
        jsonFile.create(content = this.map)
        if(save)
            jsonFile.save()
        return jsonFile
    }

    /**
     * Deletes the file.
     */
    fun delete() {
        val file = File(parent, name)
        if(file.exists()) {
            file.delete()
        }
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + map.hashCode()
        result = 31 * result + parent.hashCode()
        return result
    }
}