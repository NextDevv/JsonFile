# JsonFile
This is the JsonFile API, this class is meant to facilitate the process of creating and accessing a JSON file

Initialization of the class:

#### JAVA
```JAVA
 JsonFile file = new JsonFile("file");
 Map<String, Object> defaults = new HashMap<String, Object>() {
    {
      put("a", "1");
      put("b", "2");
      put("c", "3");
      put("d", "4");
      put("e", "5");
      put("f", "6");
    }
};

if(!file.exists()) {
  file.create((HashMap<String, Object>) defaults);
}

System.out.println(file.getString("a"));
```
#### KOTLIN
```KOTLIN
val file2 = JsonFile("file")
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
```

To get a class:
#### JAVA
```JAVA
JsonFile file = new JsonFile("file");
Car car = (Car) file.getObject("obj", Car.class);

assert car != null;
System.out.println(car.getModel());
```

#### KOTLIN
```KOTLIN
val file = JsonFile("file")
val car = (file.getObject("obj", Car::class.java) as Car?)!!

println(car.model)
```

## More updates to the wiki will come soon 🔜 !
