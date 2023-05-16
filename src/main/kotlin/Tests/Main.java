package Tests;

import json.JsonFile;
import json.JsonObject;

public class Main {
    public static void main(String[] args) {
        System.out.println("------------JAVA TEST------------");

        JsonFile json = new JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile\\src\\main\\resources\\file.json")
                .load();
        JsonObject object = json.getObject("person_1");
        Person person = object.fromJson(Person.class);
        System.out.println(person.getName());
    }
}
