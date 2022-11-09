package Tests;

import JsonFile.JsonFile;
import java.util.ArrayList;
import java.util.List;

public class JavaTest {
    public static void main(String[] args) {
        System.out.println("----------------------JAVA TEST--------------------");
        JsonFile file = new JsonFile("C:\\Users\\ciaoc\\OneDrive\\Desktop\\JsonFile.JsonFile\\src\\main\\resources", "JavaConfig");
        if(!file.exists()) file.create();

        List<Object> list = new ArrayList<Object>();
        list.add(1);
        list.add(2);
        list.add("Person");
        list.add(true);

        file.set("List", list);

        file.set("", "");

        List<Object> list2 = file.getList("List");
        for (Object o : list2) {
            System.out.println(o);
        }

        System.out.println("Price tag: "+file.getDouble("price tag"));

        JsonFile file2 = JsonFile.Methods.get("JavaConfig");
        assert file2!= null;

        System.out.println(file2.getString("price tag"));
        System.out.println(JsonFile.Methods.getAllFiles());

        System.out.println("----------------------JAVA TEST END----------------------");

        file.save();
    }
}

