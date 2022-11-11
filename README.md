![https://github.com/SpreestDev/SpreestDev/blob/main/jsonfile-logo.png](https://github.com/SpreestDev/SpreestDev/blob/main/jsonfile-logo-banner-test.png)
[![](https://jitpack.io/v/SpreestDev/JsonFile.svg)](https://jitpack.io/#SpreestDev/JsonFile)
<hr>

# JsonFile
This is the JsonFile API, this class is meant to facilitate the process of creating and accessing a JSON file

<details><summary>UPDATES</summary>
<p>

## Updates
| Version | About | Can get it |
|-----:|:---------------:| :----- |
|     V1.4.7| Fixed bug about JsonUrl              | :white_check_mark: |
|     V1.4.6| JsonUrl added              |:x:|
|     V1.4.4| Added automated Directory creation              |:white_check_mark:|
|     V1.4.3| Dependecy support added              |:white_check_mark:|
## Descriptions
**V1.4.7**
Bug fix and release

**V1.4.6**
Added the possibility to convert Json files fron url to a JsonFile class

**V1.4.4**
Added automated directory creation, to prevent any errors

**V1.4.3**
Dependecy support, so you can use JsonFile in your project

</p>
</details>

## Project Implementation

### MAVEN

#### Repository 
```XML
<repositories>
   <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
   </repository>
</repositories>
```

#### Dependency 
```XML
<dependency>
      <groupId>com.github.SpreestDev</groupId>
      <artifactId>JsonFile</artifactId>
      <version>V1.4.5</version>
</dependency>
```

### GRADLE
#### Repository 
```GRADLE
allprojects {
   repositories {
      maven { url 'https://jitpack.io' }
   }
}
```
#### Dependency 
```GRADLE
dependencies {
   implementation 'com.github.SpreestDev:JsonFile:V1.4.5'
}
```

## How to start

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

## Features Upcoming 
- [X] Automate directory creation
- [X] Read json url
