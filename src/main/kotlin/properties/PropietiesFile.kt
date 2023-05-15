package properties

import java.io.File

@Deprecated(message = "Outdated properties are no longer supported.", level = DeprecationLevel.ERROR)
/**
 * Simple file property source that reads from a file.
 */
class PropertiesFile(private var name: String) {
    private var content = hashMapOf<String, Any?>()
    private var _parent:String = ""

    constructor(parent:String, name: String) : this(name) {
        this._parent = parent
    }

    init {
        if(!name.contains("property"))
            name = "$name.property"
        val file = File(_parent,name)
        if(file.exists())
            load()
    }

    fun load() {
        val file = File(_parent,name)
        if(!file.exists()) return

        val lines = file.readLines()
        for(line in lines) {
            val parts = line.split("=")
            content[parts[0]] = parts[1]
        }
    }

    /**
     * Creates a new property file
     * @param defaults The default value for the property
     */
    fun create(defaults:HashMap<String, Any?> = hashMapOf()) {
        val file = File(_parent,name)
        if(file.exists())
            file.delete()
        file.createNewFile()
        content = defaults
        save()
    }

    fun save() {
        val file = File(_parent,name)
        content.forEach { (k,v) ->
            file.appendText("$k=$v\n")
        }
    }

    operator fun get(key:String):Any? {
        return content[key]
    }

    operator fun set(key:String, value:Any?) {
        content[key] = value
    }

    @JvmName("getT")
    operator fun <T> get(key:String):T? {
        return content[key] as T?
    }

    fun getString(key:String):String {
        return content[key] as String
    }

    fun getBoolean(key:String):Boolean {
        return content[key] as Boolean
    }

    fun getInt(key:String):Int {
        return Integer.parseInt(content[key] as String)
    }

    fun getLong(key:String):Long {
        return content[key] as Long
    }

    fun getFloat(key:String):Float {
        return content[key] as Float
    }

    fun getDouble(key:String):Double {
        return content[key] as Double
    }

    fun <T> getList(key:String):List<T> {
        return content[key] as List<T>
    }

    fun <T> getSet(key:String):Set<T> {
        return content[key] as Set<T>
    }

    fun <T> getMap(key:String):Map<String,T> {
        return content[key] as Map<String,T>
    }

    fun delete(key:String) {
        content.remove(key)
    }

    operator fun minus(key:String) {
        content.remove(key)
    }
}