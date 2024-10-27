package pl.edu.pjatk.PrzykladWyklad.model;

public class Capybara {

    private Long id;
    private String name;
    private int age;

    public Capybara() {}

    public Capybara(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public Capybara setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Capybara setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Capybara setAge(int age) {
        this.age = age;
        return this;
    }
}
