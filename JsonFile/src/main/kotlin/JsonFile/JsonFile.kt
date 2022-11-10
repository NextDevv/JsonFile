package JsonFile
import com.google.gson.GsonBuilder
import com.google.gson.JsonIOException
import java.io.*
import java.math.BigDecimal
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import kotlin.io.path.Path

/**
 * Created by Next on 07/11/2022
 *
 * An easy way to create and access JSON files
 */
class JsonFile(private var name: String) {

    companion object Methods{
        private val files = hashMapOf<String, JsonFile>()

        operator fun get(name: String): JsonFile? {
            return files[name]
        }

        /**
         * Gets all the files in the cache that are created or loaded
         * @return Return a list of all the files in the cache that are created or loaded
         */
        fun getAllFiles(): List<JsonFile> {
            return files.values.toList()
        }
    }

    private var path = "$name.json"
    private var parent = ""
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var content = mutableMapOf<String, Any?>()

    fun getParent():String {
        return parent
    }

    fun getFile():File? {
        return try {
            File(path)
        }catch (e: NullPointerException) {
            null
        }
    }

    init {
        files[name] = this
        if(exists()) {
            load()
            save()
            println(content)
        }
    }

    /**
     * Creates a JsonFile.JsonFile with the given name and specified parent path.
     */
    constructor(parent: String, name: String) : this(name) {
        this@JsonFile.path = "$parent\\$name.json"
        this@JsonFile.parent = parent

        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[JsonFile] Something went wrong!")
            println("[JsonFile] Error message: ${e.message}")
        }

        if(exists()) reload()
    }

    /**
     * Saves the contents of the file
     */
    fun save() {
        val writer = Files.newBufferedWriter(Path(path), StandardCharsets.UTF_8)
        val json = GsonBuilder().setPrettyPrinting().create().toJson(content)
        writer.write(json)
        writer.flush()
        writer.close()
    }

    /**
     * Reloads the content and the JSON file
     */
    fun reload() {
        load()
        save()
    }

    /**
     * Gets the JSON file name
     * @return the JSON file name
     */
    fun getName() = name

    private fun load() {
        val reader = Files.newBufferedReader(Path(path))
        val map:Map<*, *> = gson.fromJson(reader, Map::class.java)

        content = map as MutableMap<String, Any?>
    }

    /**
     * Returns the absolute path of the file
     * @return the absolute path of the file
     */
    fun getAbsolutePath(): String {
        return try {
            File(path).absolutePath
        }catch (e:NullPointerException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Gets the path of the JSON file
     * @return the path of the JSON file
     */
    fun getPath(): String {
        return try {
            File(path).path
        }catch (e: NullPointerException) {
            throw RuntimeException(e)
        }
    }

    /**
     * Creates a new JSON file with the given name and path, if there's already a file with the
     * same name, the function will override it
     * @param content The default content that will be written to the file
     * @return Return true if the file was successfully created, false otherwise
     */
    fun create(content: HashMap<String, Any?> = HashMap()): Boolean {
        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[JsonFile] Something went wrong!")
            println("[JsonFile] Error message: ${e.message}")
        }

        val file = File(path)
        if(!file.exists()) file.createNewFile()

        try {
            PrintWriter(FileWriter(file)).use {
                val jsonString = gson.toJson(content) ?: return false
                it.write(jsonString)
            }

            load()
            save()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * Creates a new JSON file with the given name and path, if there's already a file with the
     * same name, the function will override it
     * @param content The default content that will be written to the file
     * @return Return true if the file was successfully created, false otherwise
     */
    fun create(): Boolean {
        try {
            if(!Files.exists(Path(parent)))
                Files.createDirectories(Path(parent))
        }catch (e: Exception) {
            println("[JsonFile] Something went wrong!")
            println("[JsonFile] Error message: ${e.message}")
        }

        val file = File(path)
        if(!file.exists()) file.createNewFile()

        try {
            PrintWriter(FileWriter(file)).use {
                val jsonString = gson.toJson(mapOf<String, Any?>()) ?: return false
                it.write(jsonString)
            }

            load()
            save()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

        return true
    }

    /**
     * Sets the specified value to the specified key.
     * @param key the key to set
     * @param value the value to set
     * @return True if the key was successfully set otherwise false
     */
    operator fun set(key: String, value: Any?): Boolean {
        return try {
            content[key] = value
            true
        }catch (e: java.lang.Exception) {
            false
        }

    }

    /**
     * Gets the specified class values from the specified key
     * @param key the key to get the class values from
     * @param classObject the classObject to return the class values from
     * @return The class object values
     */
    fun <T> getObject(key: String, classObject: Class<T>): Any? {
        val jsonString = gson.toJson(content[key], classObject)
        return try {
            gson.fromJson(jsonString, classObject)
        }catch (e: JsonIOException) {
            println("Error while reading class object from file $name")
            println("Error message: ${e.message}")
            null
        }
    }

    /**
     * Puts all the values into the file
     * @param map the map of values
     */
    fun putAll(map: Map<String, Any?>) {
        map.map { (k,v) ->
            content[k] = v
        }
    }

    /**
     * Removes the specified key
     * @param key the key to remove
     * @return true if the key was removed otherwise false
     **/
    fun deleteKey(key: String): Boolean {
        val first = content.remove(key)
        return first != null
    }

    /**
     * Gets a string value from the specified key
     * @param key the key to retrieve the value from
     * @return the value
     */
    fun getString(key: String): String = content[key].toString()

    /**
     * Gets an integer value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getInt(key: String): Int {
        return try {
            content[key].toString().toInt()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a short value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getShort(key: String): Short {
        return try {
            content[key].toString().toShort()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Big Integer value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getBigInteger(key: String):BigInteger {
        return try {
            content[key].toString().toBigInteger()
        }catch (e: NumberFormatException) {
            (-1).toBigInteger()
        }
    }

    /**
     * Gets a Byte value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getByte(key: String):Byte {
        return try {
            content[key].toString().toByte()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Big Decimal value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getBigDecimal(key: String): BigDecimal {
        return try {
            content[key].toString().toBigDecimal()
        }catch (e: NumberFormatException) {
            (-1).toBigDecimal()
        }
    }

    /**
     * Gets a Long value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getLong(key: String): Long {
        return try {
            content[key].toString().toLong()
        }catch (e: NumberFormatException) {
            -1
        }
    }

    /**
     * Gets a Double value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns -1
     */
    fun getDouble(key: String): Double {
        return try {
            content[key].toString().toDouble()
        }catch (e: NumberFormatException) {
            -1.0
        }
    }

    /**
     * Checks if the file is empty
     * @return true if the file is empty otherwise false
     */
    fun isEmpty():Boolean {
        return content.isEmpty()
    }

    /**
     * This function empty the file
     *
     * REMEMBER: This function does not check if the file is empty!
     */
    fun empty() {
        content = hashMapOf()
    }

    /**
     * Gets a Boolean value from the specified key
     * @param key the key to retrieve the value from
     * @return the value retried from the specified key, if null returns false
     */
    fun getBoolean(key: String): Boolean {
        return try {
            content[key].toString().toBoolean()
        }catch (e: Exception) {
            false
        }
    }

    /**
     * Gets the string list from the json file
     * @param key the key to get the string list from
     * @return the string list from the json file
     */
    fun getStringList(key: String): List<String> {
        return try {
            content[key] as List<String>
        }catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Gets the specified list from the specified key.
     * @param key the key to get the list from
     * @return the list from the specified key
     */
    fun <T> getList(key: String): List<T> {
        return try {
            content[key] as List<T>
        }catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Checks if the JSON file exists
     * @return true if the file exists otherwise false
     */
    fun exists():Boolean {
        return try {
            File(this.path).exists()
        }catch (e: NullPointerException) {
            false
        }
    }

    /**
     * Return the contents of this file
     * @return the contents of this file
     */
    override fun toString(): String {
        if(!exists()) return ""
        val file = File(path)
        return file.readText()
    }

    /**
     * Gets a value from the specified key
     * @return Any/Object value from the specified key
     */
    operator fun get(key: String):Any? {
        return try {
            content[key]
        }catch (e: Exception) {
            null
        }
    }

    /**
     * Checks if the specified key is a string
     * @param key the key to check
     * @return true if the specified key is a string
     */
    fun isString(key: String):Boolean {
        return content[key] is String
    }

    /**
     * Checks if the specified key is an integer
     * @param key the key to check
     * @return true if the specified key is an integer
     */
    fun isInt(key: String): Boolean {
        return content[key] is Int
    }

    /**
     * Checks if the specified key is a Double
     * @param key the key to check
     * @return true if the specified key is a Double
     */
    fun isDouble(key: String): Boolean {
        return content[key] is Double
    }

    /**
     * Checks if the specified key is a Boolean
     * @param key the key to check
     * @return true if the specified key is a Boolean
     */
    fun isBoolean(key: String): Boolean {
        return content[key] is Boolean
    }

    /**
     * Checks if the specified key is a List
     * @param key the key to check
     * @return true if the specified key is a List
     */
    fun isList(key: String): Boolean {
        return content[key] is List<*>
    }

    /**
     * Checks if the key is the class specified
     * @param key the key to check
     * @return true if the specified key is the class specified
     */
    fun isInstance(key: String, type: Class<*>): Boolean {
        return type.isInstance(content[key])
    }

    /**
     * Deletes the JSON file
     */
    fun delete():Boolean {
        val file = (try { File(path) }catch (e:java.lang.Exception) {null}) ?: return false
        return file.delete()
    }
}