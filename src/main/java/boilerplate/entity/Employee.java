package boilerplate.entity;

import java.io.Serializable;

public class Employee implements Serializable {
    private int id;
    private String name;
    private String age;

    public Employee() {

    }

    public Employee(int id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
