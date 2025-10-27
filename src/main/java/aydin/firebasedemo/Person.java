package aydin.firebasedemo;

public class Person {
    private String name;
    private int age;
    private String phoneNumber;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.phoneNumber = "N/A";
    }

    public Person(String name, int age, String phone) {
        this.name = name;
        this.age = age;
        this.phoneNumber = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
